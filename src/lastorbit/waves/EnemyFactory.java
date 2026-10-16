package lastorbit.waves;

import java.awt.image.BufferedImage;

import lastorbit.entities.Alien;
import lastorbit.entities.ArmoredAlien;
import lastorbit.entities.BossAlien;
import lastorbit.entities.MiniAlien;
import lastorbit.entities.StandardAlien;
import lastorbit.interfaces.MiniFactory;
import lastorbit.util.Animation;

public class EnemyFactory {

    private final BufferedImage[] standardFrames;
    private final BufferedImage[] armoredIntact;
    private final BufferedImage[] armoredDamaged;
    private final BufferedImage[] bossFrames;
    private final BufferedImage[] miniFrames;
    private final BufferedImage[] enemyBoltFrames;
    private final int width;
    private final int height;
    private final int earthLine;
    private final int screenHeight;
    private final int screenWidth;

    public EnemyFactory(BufferedImage[] standardFrames,
                        BufferedImage[] armoredIntact,
                        BufferedImage[] armoredDamaged,
                        BufferedImage[] bossFrames,
                        BufferedImage[] miniFrames,
                        BufferedImage[] enemyBoltFrames,
                        int width, int height, int earthLine,
                        int screenWidth, int screenHeight) {
        this.standardFrames = standardFrames;
        this.armoredIntact = armoredIntact;
        this.armoredDamaged = armoredDamaged;
        this.bossFrames = bossFrames;
        this.miniFrames = miniFrames;
        this.enemyBoltFrames = enemyBoltFrames;
        this.width = width;
        this.height = height;
        this.earthLine = earthLine;
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
    }

    public Alien createStandard(double x, double y, double speed) {
        return new StandardAlien(x, y, width, height,
                                 new Animation(standardFrames, 8), speed,
                                 earthLine, screenWidth);
    }

    public Alien createArmored(double x, double y, double speed) {
        ArmoredAlien a = new ArmoredAlien(x, y, width, height,
                                          armoredIntact, armoredDamaged, speed,
                                          earthLine, screenWidth);
        a.makeShooter(enemyBoltFrames, screenWidth, 150);
        return a;
    }

    public Alien createMini(double x, double y, double speed) {
        int w = 95, h = 57;
        MiniAlien a = new MiniAlien(x, y, w, h,
                                    new Animation(miniFrames, 8), speed,
                                    earthLine, screenWidth);
        a.makeShooter(enemyBoltFrames, screenWidth, 180);
        return a;
    }

    public BossAlien createBoss(double startX, double startY, double holdX, double speed) {
        int w = 300, h = 135;
        Animation anim = new Animation(bossFrames, 8);
        MiniFactory hook = new MiniProducer(this, speed + 1.0);
        BossAlien boss = new BossAlien(startX, startY, w, h, anim, speed, earthLine,
                                       holdX, screenHeight, screenWidth, hook);
        boss.makeShooter(enemyBoltFrames, screenWidth, 90);
        return boss;
    }
}