package lastorbit.entities;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import lastorbit.interfaces.Destructible;
import lastorbit.interfaces.Movable;
import lastorbit.interfaces.Shooter;
import lastorbit.util.Animation;

public abstract class Alien extends Entity implements Movable, Destructible, Shooter {

    private int health;
    private final int maxHealth;
    private final double speed;
    private final int earthLine;
    private final int screenRight;
    private boolean reachedEarth;
    private boolean frozen;

    private boolean canShoot;
    private BufferedImage[] boltFrames;
    private int screenWidth;
    private int shootTimer;
    private int shootInterval;
    private final List<EnemyProjectile> pendingShots;

    public Alien(double x, double y, int width, int height,
                    int health, double speed, int earthLine, int screenRight) {
        super(x, y, width, height);
        this.health = health;
        this.maxHealth = health;
        this.speed = speed;
        this.earthLine = earthLine;
        this.screenRight = screenRight;
        this.reachedEarth = false;
        this.frozen = false;
        this.canShoot = false;
        this.pendingShots = new ArrayList<>();
    }

    protected abstract Animation activeAnimation();


    public void setFrozen(boolean frozen) {
        this.frozen = frozen;
    }

    protected boolean isFrozen() {
        return frozen;
    }


    protected void enableShooting(BufferedImage[] boltFrames, int screenWidth,
                                  int shootInterval) {
        this.canShoot = true;
        this.boltFrames = boltFrames;
        this.screenWidth = screenWidth;
        this.shootInterval = shootInterval;
        this.shootTimer = (int) (Math.random() * shootInterval);// for aliens to shoot at different times, not all at once
    }

    public void makeShooter(BufferedImage[] boltFrames, int screenWidth, int interval) {
        enableShooting(boltFrames, screenWidth, interval);
    }

    protected void tickShooting() {
        if (!canShoot || frozen) {
            return;
        }
        shootTimer++;
        if (shootTimer >= shootInterval) {
            shootTimer = 0;
            fireAtPlayer();
        }
    }

    private void fireAtPlayer() {
        double bx = getX();
        double by = getY() + getHeight() / 2.0 - 8;
        Animation anim = new Animation(boltFrames, 12);
        pendingShots.add(new EnemyProjectile(bx, by, 28, 14, anim, screenWidth));
    }

    @Override
    public List<EnemyProjectile> collectPendingShots() {
        List<EnemyProjectile> out = new ArrayList<>(pendingShots);
        pendingShots.clear();
        return out;
    }


    @Override
    public void moveStep() {
        if (frozen) {
            
            double enteredX = getScreenRight() - getWidth();
            if (getX() > enteredX) {
                move(-speed, 0);   // Move left until the alien is fully on screen
            }
            return;                
        }
        move(-speed, 0);
    }

    @Override
    public void update() {
        moveStep();
        activeAnimation().update();
        if (getX() <= earthLine) {
            reachedEarth = true;
        }
        tickShooting();
    }

    @Override
    public void render(Graphics2D g) {
        g.drawImage(activeAnimation().getCurrentFrame(),
                    (int) getX(), (int) getY(),
                    getWidth(), getHeight(), null);
    }


    @Override
    public void takeHit(int damage) {
        health -= damage;
        if (health <= 0) {
            health = 0;
            setAlive(false);
        }
    }

    @Override
    public boolean isDestroyed() {
        return health <= 0;
    }


    public boolean hasReachedEarth() {
        return reachedEarth;
    }

    protected double getHealthRatio() {
        return (double) health / maxHealth;
    }

    protected double getSpeedValue() {
        return speed;
    }

    protected int getScreenRight() {
        return screenRight;
    }


    public boolean isBoss() {
        return false;
    }

    public boolean dropsPickup() {
        return false;
    }


    public boolean isPickupSpawned() {
        return true;
    }

    public void markPickupSpawned() {
    }
}