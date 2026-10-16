package lastorbit.waves;


public class DifficultyController {

    private double level;
    private static final double MIN_LEVEL = 1.0;
    private static final double MAX_LEVEL = 12.0;

    private static final int FAST_CLEAR = 600;    
    private static final int SLOW_CLEAR = 1500;   

    public DifficultyController() {
        this.level = MIN_LEVEL;
    }


    public void recordWaveResult(int framesToClear, double healthRatio) {
        double delta = 0;

        if (framesToClear <= FAST_CLEAR) {
            delta += 1.0;          
        } else if (framesToClear >= SLOW_CLEAR) {
            delta -= 0.4;          
        } else {
            delta += 0.3;          
        }

        if (healthRatio >= 0.99) {
            delta += 0.6;          
        } else if (healthRatio <= 0.34) {
            delta -= 0.8;         
        }

        level += delta;
        clamp();
    }

    private void clamp() {
        if (level < MIN_LEVEL) { level = MIN_LEVEL; }
        if (level > MAX_LEVEL) { level = MAX_LEVEL; }
    }


    public WaveParameters parametersFor(int waveNumber) {
        double d = level+ waveNumber * 0.15;
        int minEnemies = (int) (4 + d);
        int maxEnemies = (int) (6 + d * 2);
        double minSpeed = 1.2 + d * 0.10;
        double maxSpeed = 1.8 + d * 0.15;
        double armoredChance = Math.min(0.15 + d * 0.05, 0.6);
        int spawnSpacing = Math.max((int) (80 - d * 4), 30);
        return new WaveParameters(minEnemies, maxEnemies,
                                  minSpeed, maxSpeed, armoredChance, spawnSpacing);
    }

    public double getLevel() {
        return level;
    }
}