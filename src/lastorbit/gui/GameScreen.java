package lastorbit.gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.List;
import java.util.Random;

import lastorbit.combat.CollisionManager;
import lastorbit.combat.PlayerHitManager;
import lastorbit.components.StandardWeapon;
import lastorbit.components.Weapon;
import lastorbit.core.InputHandler;
import lastorbit.entities.Alien;
import lastorbit.entities.EnemyProjectile;
import lastorbit.entities.PlayerProjectile;
import lastorbit.entities.PowerUp;
import lastorbit.entities.Spaceship;
import lastorbit.exceptions.AssetLoadException;
import lastorbit.rules.GameContext;
import lastorbit.rules.GameState;
import lastorbit.rules.RuleEngine;
import lastorbit.score.HighScoreStore;
import lastorbit.score.ScoreEntry;
import lastorbit.upgrades.FreezeUpgrade;
import lastorbit.upgrades.ShieldUpgrade;
import lastorbit.upgrades.Upgrade;
import lastorbit.upgrades.UpgradeContext;
import lastorbit.upgrades.WeaponUpgrade;
import lastorbit.util.Animation;
import lastorbit.util.EntityManager;
import lastorbit.util.ResourceLoader;
import lastorbit.waves.EnemyFactory;
import lastorbit.waves.WaveManager;

public class GameScreen extends Screen {

    private static final int EARTH_LINE = 20;
    private static final int PLAYER_HEALTH = 5;
    private static final String SCORE_FILE = "resources/highscores.txt";

    private final int width;
    private final int height;
    private final BufferedImage background;
    private final ScreenFactory factory;
    private final RuleEngine ruleEngine;
    private final int streakThreshold;  

    private final InputHandler input;
    private final Spaceship player;
    private final EntityManager<PlayerProjectile> projectiles;
    private final EntityManager<EnemyProjectile> enemyShots;
    private final EntityManager<Alien> aliens;
    private final EntityManager<PowerUp> powerUps;
    private final WaveManager waveManager;
    private final GameContext ruleContext;

    private final BufferedImage shieldIcon;
    private final BufferedImage freezeIcon;
    private final BufferedImage weaponIcon;
    private final Random random = new Random();

    private boolean gameOver;
    private boolean paused;
    private int score;
    private int killStreak;

    private final HighScoreStore scoreStore;
    private boolean enteringName;

    @SuppressWarnings("unused")
    private boolean scoreSaved;
    
    private String nameEntry = "";
    private static final int MAX_NAME = 8;

    private final Button resumeButton;
    private final Button restartFromPauseButton;
    private final Button menuFromPauseButton;
    private final Button restartButton;
    private final Button menuButton;

    public GameScreen(int width, int height, BufferedImage background,
                      RuleEngine ruleEngine, ScreenFactory factory)
            throws AssetLoadException {
        this.width = width;
        this.height = height;
        this.background = background;
        this.ruleEngine = ruleEngine;
        this.streakThreshold = (ruleEngine != null) ? ruleEngine.getStreakThreshold() : -1;
        this.factory = factory;
        this.gameOver = false;
        this.paused = false;
        this.score = 0;
        this.killStreak = 0;

        this.scoreStore = new HighScoreStore(SCORE_FILE);
        this.enteringName = false;
        this.scoreSaved = false;

        this.input = new InputHandler();

        BufferedImage[] playerFrames = ResourceLoader.loadFrames("player", 4);
        Animation playerAnim = new Animation(playerFrames, 10);

        this.projectiles = new EntityManager<>();
        this.enemyShots = new EntityManager<>();

        BufferedImage[] boltFrames = ResourceLoader.loadFrames("proj_player", 3);
        Weapon weapon = new StandardWeapon(width, boltFrames);

        int shipWidth = 180, shipHeight = 71;
        double startX = 90;
        double startY = (height - shipHeight) / 2.0;
        this.player = new Spaceship(startX, startY, shipWidth, shipHeight,
                                    input, playerAnim, 6.0, height,
                                    weapon, boltFrames, projectiles, 10,
                                    PLAYER_HEALTH, width);

        BufferedImage[] standardFrames = ResourceLoader.loadFrames("standard_alien", 4);
        BufferedImage[] armoredIntact  = ResourceLoader.loadFrames("armored_intact", 4);
        BufferedImage[] armoredDamaged = ResourceLoader.loadFrames("armored_damaged", 4);
        BufferedImage[] bossFrames     = ResourceLoader.loadFrames("boss", 4);
        BufferedImage[] miniFrames     = ResourceLoader.loadFrames("mini", 4);
        BufferedImage[] enemyBolt      = ResourceLoader.loadFrames("proj_enemy", 3);

        this.aliens = new EntityManager<>();
        EnemyFactory enemyFactory = new EnemyFactory(
            standardFrames, armoredIntact, armoredDamaged, bossFrames, miniFrames,
            enemyBolt, 120, 72, EARTH_LINE, width, height);
        this.waveManager = new WaveManager(enemyFactory, width, height);

        this.shieldIcon = ResourceLoader.loadImage("resources/sprites/shield_icon.png");
        this.freezeIcon = ResourceLoader.loadImage("resources/sprites/freeze_icon.png");
        this.weaponIcon = ResourceLoader.loadImage("resources/sprites/weapon_icon.png");
        this.powerUps = new EntityManager<>();

        UpgradeContext upgradeContext = new UpgradeContext(player, aliens);
        player.setUpgradeContext(upgradeContext);
        this.ruleContext = new GameContext(player, aliens);

        int cx = width / 2 - 130;
        this.resumeButton            = new Button(cx, 300, 260, 52, "RESUME");
        this.restartFromPauseButton  = new Button(cx, 364, 260, 52, "RESTART");
        this.menuFromPauseButton     = new Button(cx, 428, 260, 52, "MAIN MENU");
        this.restartButton           = new Button(cx, height - 130, 260, 52, "RESTART");
        this.menuButton              = new Button(cx, height - 70, 260, 52, "MAIN MENU");
    }

    @Override
    public void handleKeyPressed(int keyCode) {
        if (enteringName) {
            handleNameKey(keyCode);
            return;
        }
        if ((keyCode == KeyEvent.VK_P || keyCode == KeyEvent.VK_ESCAPE) && !gameOver) {
            paused = !paused;
            return;
        }
        input.keyPressedCode(keyCode);
    }

    @Override
    public void handleKeyReleased(int keyCode) {
        if (enteringName) {
            return;
        }
        input.keyReleasedCode(keyCode);
    }

    private void handleNameKey(int keyCode) {
        if (keyCode == KeyEvent.VK_ENTER) {
            confirmName();
        } else if (keyCode == KeyEvent.VK_BACK_SPACE) {
            if (nameEntry.length() > 0) {
                nameEntry = nameEntry.substring(0, nameEntry.length() - 1);
            }
        } else if (nameEntry.length() < MAX_NAME) {
            if (keyCode >= KeyEvent.VK_A && keyCode <= KeyEvent.VK_Z) {
                nameEntry += (char) ('A' + (keyCode - KeyEvent.VK_A));
            } else if (keyCode >= KeyEvent.VK_0 && keyCode <= KeyEvent.VK_9) {
                nameEntry += (char) ('0' + (keyCode - KeyEvent.VK_0));
            }
        }
    }

    private void confirmName() {
        String name = nameEntry.isEmpty() ? "PLAYER" : nameEntry;
        scoreStore.addScore(new ScoreEntry(name, score, waveManager.getWaveNumber()));
        enteringName = false;
        scoreSaved = true;
    }

    @Override
    public void update() {
        if (gameOver || paused) {
            return;
        }
        boolean waveActive = !waveManager.isBetweenWaves();
        player.setFiringEnabled(waveActive);
        player.update();

        projectiles.updateAll();
        enemyShots.updateAll();

        waveManager.update(aliens);
        if (waveManager.justClearedWave()) {
            double healthRatio = (double) player.getHealth() / player.getMaxHealth();
            waveManager.reportWaveCleared(healthRatio);
            player.heal(2);
        }

        spawnPickupOnBossDeath();
        aliens.updateAll();
        for (Alien alien : aliens.getEntities()) {
            for (EnemyProjectile shot : alien.collectPendingShots()) {
                enemyShots.add(shot);
            }
        }

        powerUps.updateAll();
        collectPowerUps();

        int kills = CollisionManager.resolve(projectiles, aliens);
        if (kills > 0) {
            score += kills;
            killStreak += kills;
            player.rewardKill(kills);
        }

         PlayerHitManager.resolve(enemyShots, player);
        if (player.isDestroyed()) {
            triggerGameOver();
        }
        
        if (ruleEngine != null) {
            GameState state = buildGameState();
            ruleEngine.evaluateAll(state, ruleContext);
            honorBossRequest();
        }

        if (streakThreshold > 0 && killStreak >= streakThreshold) {
            killStreak = 0;
        }

        for (Alien alien : aliens.getEntities()) {
            if (alien.hasReachedEarth()) {
                killStreak = 0;
                triggerGameOver();
                break;
            }
        }
    }

    private void triggerGameOver() {
        if (gameOver) {
            return;
        }
        gameOver = true;
        if (scoreStore.qualifies(score)) {
            enteringName = true;
        }
    }

    private GameState buildGameState() {
        boolean bossActive = false;
        int count = aliens.count();
        double nearestX = width;
        for (Alien a : aliens.getEntities()) {
            if (a.isBoss()) {
                bossActive = true;
            }
            if (a.getX() < nearestX) {
                nearestX = a.getX();
            }
        }
        return new GameState(killStreak, player.getHealth(), player.getMaxHealth(),
                             bossActive, count, nearestX, width);
    }

    private void honorBossRequest() {
        if (ruleContext.isBossRequested()) {
            waveManager.spawnBossNow(aliens);
            ruleContext.clearBossRequest();
        }
    }

    private void spawnPickupOnBossDeath() {
        for (Alien alien : aliens.getEntities()) {
            if (alien.dropsPickup() && alien.isDestroyed() && !alien.isPickupSpawned()) {
                alien.markPickupSpawned();
                double px = alien.getX() + alien.getWidth() / 2.0;
                double py = alien.getY() + alien.getHeight() / 2.0;
                powerUps.add(makeRandomPickup(px, py));
            }
        }
    }

    private PowerUp makeRandomPickup(double x, double y) {
        int roll = random.nextInt(3);
        Upgrade upgrade;
        BufferedImage icon;
        if (roll == 0) {
            upgrade = new ShieldUpgrade(); icon = shieldIcon;
        } else if (roll == 1) {
            upgrade = new FreezeUpgrade(); icon = freezeIcon;
        } else {
            upgrade = new WeaponUpgrade(); icon = weaponIcon;
        }
        return new PowerUp(x, y, 48, icon, upgrade, 2.0);
    }

    private void collectPowerUps() {
        for (PowerUp pu : powerUps.getEntities()) {
            if (pu.isAlive() && pu.getBounds().intersects(player.getBounds())) {
                player.applyUpgrade(pu.collect());
            }
        }
    }

    @Override
    public void render(Graphics2D g) {
        g.drawImage(background, 0, 0, width, height, null);
        aliens.renderAll(g);
        projectiles.renderAll(g);
        enemyShots.renderAll(g);
        powerUps.renderAll(g);
        player.render(g);
        drawHud(g);

        if (waveManager.isBetweenWaves() && !gameOver && !paused) {
            g.setColor(Color.CYAN);
            g.setFont(new Font("Consolas", Font.BOLD, 48));
            g.drawString("WAVE " + (waveManager.getWaveNumber() + 1),
                         width / 2 - 120, height / 2);
        }

        if (paused) {
            drawPauseOverlay(g);
        }
        if (gameOver) {
            drawGameOverOverlay(g);
        }
    }

    private void drawPauseOverlay(Graphics2D g) {
        g.setColor(new Color(0, 0, 0, 170));
        g.fillRect(0, 0, width, height);
        g.setColor(new Color(120, 200, 255));
        g.setFont(new Font("Consolas", Font.BOLD, 56));
        g.drawString("PAUSED", width / 2 - 110, 230);
        resumeButton.render(g, false);
        restartFromPauseButton.render(g, false);
        menuFromPauseButton.render(g, false);
    }

    private void drawGameOverOverlay(Graphics2D g) {
        g.setColor(new Color(0, 0, 0, 185));
        g.fillRect(0, 0, width, height);
        g.setColor(Color.RED);
        g.setFont(new Font("Consolas", Font.BOLD, 60));
        g.drawString("GAME OVER", width / 2 - 180, 110);
        g.setColor(Color.WHITE);
        g.setFont(new Font("Consolas", Font.BOLD, 24));
        g.drawString("Score: " + score + "    Wave: " + waveManager.getWaveNumber(),
                     width / 2 - 150, 150);

        if (enteringName) {
            g.setColor(Color.YELLOW);
            g.setFont(new Font("Consolas", Font.BOLD, 28));
            g.drawString("NEW HIGH SCORE!", width / 2 - 140, 210);
            g.setColor(Color.WHITE);
            g.setFont(new Font("Consolas", Font.PLAIN, 22));
            g.drawString("Enter name and press ENTER:", width / 2 - 170, 250);
            g.setColor(new Color(20, 40, 70));
            g.fillRoundRect(width / 2 - 120, 270, 240, 44, 10, 10);
            g.setColor(new Color(120, 200, 255));
            g.drawRoundRect(width / 2 - 120, 270, 240, 44, 10, 10);
            g.setColor(Color.WHITE);
            g.setFont(new Font("Consolas", Font.BOLD, 26));
            g.drawString(nameEntry + "_", width / 2 - 105, 300);
        } else {
            drawHighScores(g, 200);
            restartButton.render(g, false);
            menuButton.render(g, false);
        }
    }

    private void drawHighScores(Graphics2D g, int startY) {
        g.setColor(new Color(120, 200, 255));
        g.setFont(new Font("Consolas", Font.BOLD, 26));
        g.drawString("HIGH SCORES", width / 2 - 100, startY);

        List<ScoreEntry> list = scoreStore.getEntries();
        g.setFont(new Font("Consolas", Font.PLAIN, 20));
        int y = startY + 40;
        int rank = 1;
        for (ScoreEntry e : list) {
            g.setColor(Color.WHITE);
            String row = String.format("%d.  %-8s  %6d   W%d",
                                       rank, e.getName(), e.getScore(), e.getWave());
            g.drawString(row, width / 2 - 160, y);
            y += 30;
            rank++;
        }
        if (list.isEmpty()) {
            g.setColor(Color.GRAY);
            g.drawString("No scores yet.", width / 2 - 80, y);
        }
    }

    @Override
    public void handleClick(int x, int y) {
        if (enteringName) {
            return;
        }
        if (paused) {
            if (resumeButton.contains(x, y)) {
                paused = false;
            } else if (restartFromPauseButton.contains(x, y)) {
                restart();
            } else if (menuFromPauseButton.contains(x, y)) {
                getManager().setScreen(factory.createMenu());
            }
            return;
        }
        if (gameOver) {
            if (restartButton.contains(x, y)) {
                restart();
            } else if (menuButton.contains(x, y)) {
                getManager().setScreen(factory.createMenu());
            }
        }
    }

    private void restart() {
        try {
            getManager().setScreen(factory.createGame(ruleEngine));
        } catch (AssetLoadException e) {
            getManager().setScreen(factory.createMenu());
        }
    }

    private void drawHud(Graphics2D g) {
        g.setColor(Color.WHITE);
        g.setFont(new Font("Consolas", Font.BOLD, 20));
        g.drawString("SCORE: " + score, 20, 30);
        g.drawString("WAVE: " + waveManager.getWaveNumber(), 20, 55);
        if (streakThreshold > 0) {
            g.drawString("STREAK: " + killStreak, 20, 80);
        }

        int barX = 20, barY = 100, barW = 200, barH = 16;
        g.setColor(Color.DARK_GRAY);
        g.fillRect(barX, barY, barW, barH);
        double ratio = (double) player.getHealth() / player.getMaxHealth();
        g.setColor(ratio > 0.3 ? Color.GREEN : Color.RED);
        g.fillRect(barX, barY, (int) (barW * ratio), barH);
        g.setColor(Color.WHITE);
        g.drawRect(barX, barY, barW, barH);

        g.setFont(new Font("Consolas", Font.PLAIN, 14));
        g.setColor(Color.LIGHT_GRAY);
        g.drawString("P = pause", width - 110, 28);

        Upgrade up = player.getActiveUpgrade();
        if (up != null) {
            g.setColor(Color.YELLOW);
            g.setFont(new Font("Consolas", Font.BOLD, 18));
            int secs = up.getRemainingFrames() / 60 + 1;
            g.drawString(up.getLabel() + "  " + secs + "s", 20, 145);
        }
    }
}