package lastorbit.components;

import java.awt.image.BufferedImage;
import java.util.List;

import lastorbit.entities.PlayerProjectile;
import lastorbit.util.Animation;


public abstract class Weapon {

    private final int screenWidth;
    private final BufferedImage[] boltFrames;

    public Weapon(int screenWidth, BufferedImage[] boltFrames) {
        this.screenWidth = screenWidth;
        this.boltFrames = boltFrames;
    }

    public PlayerProjectile fire(double x, double y) {
        Animation anim = new Animation(boltFrames, 12);
        return new PlayerProjectile(x, y, 34, 16, anim, screenWidth);
    }

    public abstract List<PlayerProjectile> fire(int spreadCount, double x, double y);

    protected BufferedImage[] getBoltFrames() { return boltFrames; }
    protected int getScreenWidth() { return screenWidth; }
}