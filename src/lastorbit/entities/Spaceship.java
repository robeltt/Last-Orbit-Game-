package lastorbit.entities;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import lastorbit.components.Shield;
import lastorbit.components.SpreadWeapon;
import lastorbit.components.StandardWeapon;
import lastorbit.components.Weapon;
import lastorbit.interfaces.Destructible;
import lastorbit.upgrades.Upgrade;
import lastorbit.upgrades.UpgradeContext;
import lastorbit.util.Animation;
import lastorbit.util.EntityManager;
import lastorbit.util.InputSource;

public class Spaceship extends Entity implements Destructible {

    private final InputSource input;
    private final Animation animation;
    private final double speed;
    private final int minY;
    private final int maxY;
    private final int screenWidth;

    private Weapon weapon;
    private final BufferedImage[] boltFrames;
    private final EntityManager<PlayerProjectile> projectiles;
    private int fireRate;
    private int fireCooldown;
    private boolean firingEnabled;
    private boolean spreadEnabled;

    private int health;
    private final int maxHealth;
    private int invulnTimer;
    private static final int INVULN_FRAMES = 45;

    private Shield shield;

    private Upgrade activeUpgrade;
    private UpgradeContext upgradeContext;

    public Spaceship(double x, double y, int width, int height,
                     InputSource input, Animation animation, double speed,
                     int screenHeight, Weapon weapon, BufferedImage[] boltFrames,
                     EntityManager<PlayerProjectile> projectiles, int fireRate,
                     int maxHealth, int screenWidth) {
        super(x, y, width, height);
        this.input = input;
        this.animation = animation;
        this.speed = speed;
        this.minY = 0;
        this.maxY = screenHeight - height;
        this.screenWidth = screenWidth;
        this.weapon = weapon;
        this.boltFrames = boltFrames;
        this.projectiles = projectiles;
        this.fireRate = fireRate;
        this.fireCooldown = 0;
        this.firingEnabled = false;
        this.spreadEnabled = false;
        this.maxHealth = maxHealth;
        this.health = maxHealth;
        this.invulnTimer = 0;
        this.shield = null;
        this.activeUpgrade = null;
    }

    public void setFiringEnabled(boolean enabled) {
        this.firingEnabled = enabled;
    }

    public void setUpgradeContext(UpgradeContext ctx) {
        this.upgradeContext = ctx;
    }

    @Override
    public void update() {
        double dy = 0;
        if (input.isUpPressed())   { dy -= speed; }
        if (input.isDownPressed()) { dy += speed; }
        move(0, dy);
        clampVertical();
        animation.update();
        handleFiring();
        if (invulnTimer > 0) {
            invulnTimer--;
        }
        if (shield != null) {
            shield.update();
            if (!shield.isActive()) {
                shield = null;
            }
        }
        if (activeUpgrade != null) {
            activeUpgrade.update(upgradeContext);
            if (!activeUpgrade.isActive()) {
                activeUpgrade = null;
            }
        }
    }

    private void clampVertical() {
        if (getY() < minY)      { setPosition(getX(), minY); }
        else if (getY() > maxY) { setPosition(getX(), maxY); }
    }

    private void handleFiring() {
        if (!firingEnabled) {
            return;
        }
        if (fireCooldown > 0) {//
            fireCooldown--;
            return;
        }
        double muzzleX = getX() + getWidth();
        double muzzleY = getY() + getHeight() / 2.0 - 8;
        if (spreadEnabled) {
            for (PlayerProjectile shot : weapon.fire(3, muzzleX, muzzleY)) {
                projectiles.add(shot);
            }
        } else {
            projectiles.add(weapon.fire(muzzleX, muzzleY));
        }
        fireCooldown = fireRate;
    }

    public void rewardKill(int kills) {
    }

    public void heal(int amount) {
        health += amount;
        if (health > maxHealth) {
            health = maxHealth;
        }
    }

    public void applyUpgrade(Upgrade upgrade) {
        if (activeUpgrade != null) {
            activeUpgrade.expire(upgradeContext);
        }
        activeUpgrade = upgrade;
        upgrade.activate(upgradeContext);
    }

    public Upgrade getActiveUpgrade() {
        return activeUpgrade;
    }

    public void setSpreadEnabled(boolean enabled) {
        if (enabled == spreadEnabled) {
            return;
        }
        spreadEnabled = enabled;
        this.weapon = enabled
            ? new SpreadWeapon(screenWidth, boltFrames)
            : new StandardWeapon(screenWidth, boltFrames);
    }

    public void setFireRate(int frames) {
        this.fireRate = frames;
    }

    public void attachShield(Shield shield) {
        this.shield = shield;
    }

    public void detachShield() {
        this.shield = null;
    }

    @Override
    public void takeHit(int damage) {
        if (shield != null && shield.absorbHit()) {
            return;
        }
        if (invulnTimer > 0) {
            return;
        }
        health -= damage;
        if (health < 0) {
            health = 0;
        }
        invulnTimer = INVULN_FRAMES;//start invulnerability frames after taking damage
        if (health <= 0) {
            setAlive(false);
        }
    }

    @Override
    public boolean isDestroyed() {
        return health <= 0;
    }

    public int getHealth()    { return health; }
    public int getMaxHealth() { return maxHealth; }
    public boolean isInvulnerable() { return invulnTimer > 0; }
    public boolean hasShield() { return shield != null; }

    @Override
    public void render(Graphics2D g) {
        if (invulnTimer > 0 && (invulnTimer / 4) % 2 == 0) {//blink effect every 4 frames during invulnerability
            if (shield != null) {
                shield.render(g, (int) getX(), (int) getY(), getWidth(), getHeight());
            }
            return;
        }
        g.drawImage(animation.getCurrentFrame(),
                    (int) getX(), (int) getY(),
                    getWidth(), getHeight(), null);
        if (shield != null) {
            shield.render(g, (int) getX(), (int) getY(), getWidth(), getHeight());
        }
    }
}