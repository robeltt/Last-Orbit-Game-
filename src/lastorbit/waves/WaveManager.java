package lastorbit.waves;

import java.util.Random;

import lastorbit.entities.Alien;
import lastorbit.util.EntityManager;

public class WaveManager {

    private final EnemyFactory factory;
    private final int screenWidth;
    private final int screenHeight;
    private final Random random;
    private final DifficultyController difficulty;

    private int waveNumber;
    private Wave currentWave;
    private boolean betweenWaves;
    private int interWaveTimer;
    private static final int INTER_WAVE_DELAY = 120;

    private boolean waveJustCleared;
    private int waveFrameCounter;

    public WaveManager(EnemyFactory factory, int screenWidth, int screenHeight) {
        this.factory = factory;
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
        this.random = new Random();
        this.difficulty = new DifficultyController();
        this.waveNumber = 0;
        this.betweenWaves = true;
        this.interWaveTimer = 0;
        this.currentWave = null;
        this.waveJustCleared = false;
        this.waveFrameCounter = 0;
    }

    public void update(EntityManager<Alien> aliens) {
        waveJustCleared = false;

        if (betweenWaves) {
            interWaveTimer++;
            if (interWaveTimer >= INTER_WAVE_DELAY) {
                startNextWave();
            }
            return;
        }

        waveFrameCounter++;
        currentWave.update(aliens);

        if (currentWave.isComplete(aliens)) {
            betweenWaves = true;
            interWaveTimer = 0;
            waveJustCleared = true;
        }
    }

    private void startNextWave() {
        waveNumber++;
        betweenWaves = false;
        waveFrameCounter = 0;
        WaveParameters params = difficulty.parametersFor(waveNumber);

        if (waveNumber % 5 == 0) {
            currentWave = new BossWave(params, factory, screenWidth, screenHeight, random);
        } else {
            currentWave = new StandardWave(params, factory, screenWidth, screenHeight, random);
        }
    }

    public void reportWaveCleared(double healthRatio) {
        difficulty.recordWaveResult(waveFrameCounter, healthRatio);
    }
   public void spawnBossNow(EntityManager<Alien> aliens) {
    double holdX = screenWidth - 380;
    aliens.add(factory.createBoss(screenWidth, 200, holdX, 1.5));
   }

    public boolean justClearedWave() {
        return waveJustCleared;
    }

    public int getWaveNumber() { return waveNumber; }
    public boolean isBetweenWaves() { return betweenWaves; }
}