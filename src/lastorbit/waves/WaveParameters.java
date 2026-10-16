package lastorbit.waves;


public class WaveParameters {

    private final int minEnemies;
    private final int maxEnemies;
    private final double minSpeed;
    private final double maxSpeed;
    private final double armoredChance;   
    private final int spawnSpacing;       

    public WaveParameters(int minEnemies, int maxEnemies,
                          double minSpeed, double maxSpeed,
                          double armoredChance, int spawnSpacing) {
        this.minEnemies = minEnemies;
        this.maxEnemies = maxEnemies;
        this.minSpeed = minSpeed;
        this.maxSpeed = maxSpeed;
        this.armoredChance = armoredChance;
        this.spawnSpacing = spawnSpacing;
    }

    public int getMinEnemies()     { return minEnemies; }
    public int getMaxEnemies()     { return maxEnemies; }
    public double getMinSpeed()    { return minSpeed; }
    public double getMaxSpeed()    { return maxSpeed; }
    public double getArmoredChance() { return armoredChance; }
    public int getSpawnSpacing()   { return spawnSpacing; }
}
