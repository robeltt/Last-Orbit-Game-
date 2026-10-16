package lastorbit.waves;

import java.util.List;
import java.util.Random;

import lastorbit.entities.Alien;
import lastorbit.entities.BossAlien;
import lastorbit.util.EntityManager;


public class BossWave extends Wave {

    private final double screenWidthValue;
    private BossAlien boss;
    private boolean bossSpawned;

    public BossWave(WaveParameters params, EnemyFactory factory,
                    int screenWidth, int screenHeight, Random random) {
        super(params, factory, screenWidth, screenHeight, random);
        this.screenWidthValue = screenWidth;
        this.bossSpawned = false;
    }

    @Override
    public void update(EntityManager<Alien> aliens) {
        if (!bossSpawned) {
            double startX = screenWidthValue;
            double startY = 200;
            double holdX = screenWidthValue - 380;   
            boss = factory().createBoss(startX, startY, holdX, 1.5);
            aliens.add(boss);
            bossSpawned = true;
            return;
        }
        if (boss != null && boss.isAlive()) {
            List<Alien> children = boss.collectPendingChildren();
            for (Alien child : children) {
                aliens.add(child);
            }
        }
    }

    @Override
    protected Alien spawnOne() {
        return null;   
    }

    @Override
    public boolean isFinishedSpawning() {
        return bossSpawned;
    }

    @Override
    public boolean isComplete(EntityManager<Alien> aliens) {
        return bossWasDefeated() && aliens.count() == 0;
    }

    public boolean bossWasDefeated() {
        return bossSpawned && boss != null && boss.isDestroyed();
    }
}