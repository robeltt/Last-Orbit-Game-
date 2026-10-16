package lastorbit.entities;

import java.util.ArrayList;
import java.util.List;

import lastorbit.interfaces.MiniFactory;
import lastorbit.interfaces.MiniSpawner;
import lastorbit.util.Animation;

public class BossAlien extends Alien implements MiniSpawner {

    private final Animation animation;
    private final List<Alien> pendingChildren;

    private final double patrolCenterY;
    private final double patrolRange;
    private double phase;

    private final double holdX;
    private boolean arrived;

    private final MiniFactory miniFactory;
    private int spawnTimer;
    private static final int SPAWN_INTERVAL = 150;
    private static final int BOSS_HEALTH = 40;

    private boolean pickupSpawned;

    public BossAlien(double x, double y, int width, int height,
                     Animation animation, double speed, int earthLine,
                     double holdX, int screenHeight, int screenRight,
                     MiniFactory miniFactory) {
        super(x, y, width, height, BOSS_HEALTH, speed, earthLine, screenRight);
        this.animation = animation;
        this.pendingChildren = new ArrayList<>();
        this.holdX = holdX;
        this.arrived = false;
        this.patrolCenterY = (screenHeight - height) / 2.0;
        this.patrolRange = (screenHeight - height) / 2.0 - 20;
        this.phase = 0;
        this.miniFactory = miniFactory;
        this.spawnTimer = 0;
        this.pickupSpawned = false;
    }

    @Override
    public void moveStep() {
        if (isFrozen()) {
            return;
        }
        if (!arrived) {
            move(-getSpeedValue(), 0);
            if (getX() <= holdX) {
                arrived = true;
            }
        } else {
            phase += 0.03;
            double targetY = patrolCenterY + Math.sin(phase) * patrolRange;
            setPosition(holdX, targetY);
        }
    }

    @Override
    public void update() {
        moveStep();
        animation.update();
        if (arrived && !isFrozen()) {
            spawnTimer++;
            if (spawnTimer >= SPAWN_INTERVAL) {
                spawnTimer = 0;
                produceMinis();
            }
            tickShooting();
        }
    }

    private void produceMinis() {
        int count = 2 + (int) (Math.random() * 2);
        for (int i = 0; i < count; i++) {
            double spawnX = getX();
            double spawnY = getY() + getHeight() / 2.0 + (Math.random() * 60 - 30);
            pendingChildren.add(miniFactory.create(spawnX, spawnY));
        }
    }

    @Override
    public List<Alien> collectPendingChildren() {
        List<Alien> out = new ArrayList<>(pendingChildren);
        pendingChildren.clear();
        return out;
    }

    @Override
    public boolean isPickupSpawned() {
        return pickupSpawned;
    }

    public void markPickupSpawned() {
        this.pickupSpawned = true;
    }

    @Override
    protected Animation activeAnimation() {
        return animation;
    }

    @Override
    public boolean isBoss() {
        return true;
    }

    @Override
    public boolean dropsPickup() {
        return true;
    }
}