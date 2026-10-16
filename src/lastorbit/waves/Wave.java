package lastorbit.waves;

import java.util.Random;

import lastorbit.entities.Alien;
import lastorbit.util.EntityManager;


public abstract class Wave {

    private final WaveParameters params;
    private final EnemyFactory factory;
    private final int screenWidth;
    private final int screenHeight;
    private final Random random;

    private final int totalToSpawn;
    private int spawnedCount;
    private int spawnTimer;

    public Wave(WaveParameters params, EnemyFactory factory,
                   int screenWidth, int screenHeight, Random random) {
        this.params = params;
        this.factory = factory;
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
        this.random = random;
        this.totalToSpawn = rollBetween(params.getMinEnemies(), params.getMaxEnemies());
        this.spawnedCount = 0;
        this.spawnTimer = 0;
    }

    public void update(EntityManager<Alien> aliens) {
        if (isFinishedSpawning()) {
            return;
        }
        spawnTimer++;
        if (spawnTimer >= params.getSpawnSpacing()) {
            spawnTimer = 0;
            aliens.add(spawnOne());
            spawnedCount++;
        }
    }

    protected abstract Alien spawnOne();

    public boolean isFinishedSpawning() {
        return spawnedCount >= totalToSpawn;
    }

    public boolean isComplete(EntityManager<Alien> aliens) {
        return isFinishedSpawning() && aliens.count() == 0;
    }


    protected double randomY() {
        return random.nextInt(Math.max(1, screenHeight - 72));
    }

    protected double spawnX() {
        return screenWidth;   
    }

    protected double rollSpeed() {
        double min = params.getMinSpeed();
        double max = params.getMaxSpeed();
        return min + random.nextDouble() * (max - min);
    }

    protected boolean rollArmored() {
        return random.nextDouble() < params.getArmoredChance();
    }

    protected EnemyFactory factory() {
        return factory;
    }

    private int rollBetween(int min, int max) {
        if (max <= min) {
            return min;
        }
        return min + random.nextInt(max - min + 1);
    }
}