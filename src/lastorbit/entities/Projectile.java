package lastorbit.entities;

import java.awt.Graphics2D;

import lastorbit.interfaces.Damaging;
import lastorbit.interfaces.Movable;
import lastorbit.util.Animation;

public abstract class Projectile extends Entity implements Movable, Damaging {

    private final Animation animation;
    private final double velocityX;
    private final int screenWidth;
    private final int damage;

    public Projectile(double x, double y, int width, int height,
                         Animation animation, double velocityX,
                         int screenWidth, int damage) {
        super(x, y, width, height);
        this.animation = animation;
        this.velocityX = velocityX;
        this.screenWidth = screenWidth;
        this.damage = damage;
    }

    @Override
    public void moveStep() {
        move(velocityX, 0);
    }

    @Override
    public void update() {
        moveStep();
        animation.update();
        if (getX() + getWidth() < 0 || getX() > screenWidth) {
            setAlive(false);
        }
    }

    @Override
    public void render(Graphics2D g) {
        g.drawImage(animation.getCurrentFrame(),
                    (int) getX(), (int) getY(),
                    getWidth(), getHeight(), null);
    }

    @Override
    public int getDamage() {
        return damage;
    }

    @Override
    public void onHit() {
        setAlive(false); 
    }
}