package lastorbit.rules;

public class GameState {

    private final int killStreak;
    private final int playerHealth;
    private final int playerMaxHealth;
    private final boolean bossActive;
    private final int aliensOnScreen;
    private final double nearestAlienX;
    private final int screenWidth;

    public GameState(int killStreak, int playerHealth, int playerMaxHealth,
                     boolean bossActive, int aliensOnScreen,
                     double nearestAlienX, int screenWidth) {
        this.killStreak = killStreak;
        this.playerHealth = playerHealth;
        this.playerMaxHealth = playerMaxHealth;
        this.bossActive = bossActive;
        this.aliensOnScreen = aliensOnScreen;
        this.nearestAlienX = nearestAlienX;
        this.screenWidth = screenWidth;
    }

    public int getKillStreak()      { return killStreak; }
    public int getPlayerHealth()    { return playerHealth; }
    public int getPlayerMaxHealth() { return playerMaxHealth; }
    public boolean isBossActive()   { return bossActive; }
    public int getAliensOnScreen()  { return aliensOnScreen; }
    public double getNearestAlienX(){ return nearestAlienX; }
    public int getScreenWidth()     { return screenWidth; }
}