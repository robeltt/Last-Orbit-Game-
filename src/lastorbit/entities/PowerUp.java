package lastorbit.entities;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import lastorbit.upgrades.Upgrade;


public class PowerUp extends Entity {

    private final BufferedImage icon;
    private final Upgrade upgrade;
    private final double speed;

    public PowerUp(double x, double y, int size, BufferedImage icon,
                   Upgrade upgrade, double speed) {
        super(x, y, size, size);
        this.icon = icon;
        this.upgrade = upgrade;
        this.speed = speed;
    }

    @Override
    public void update() {
        move(-speed, 0);
        if (getX() + getWidth() < 0) {
            setAlive(false);   
        }
    }

    @Override
    public void render(Graphics2D g) {
        g.drawImage(icon, (int) getX(), (int) getY(),
                    getWidth(), getHeight(), null);
    }

    public Upgrade collect() {
        setAlive(false);
        return upgrade;
    }
}