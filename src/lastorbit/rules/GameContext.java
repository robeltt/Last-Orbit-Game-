package lastorbit.rules;

import lastorbit.components.Shield;
import lastorbit.entities.Alien;
import lastorbit.entities.Spaceship;
import lastorbit.util.EntityManager;


public class GameContext {

    private final Spaceship player;
    private final EntityManager<Alien> aliens;
    private boolean bossRequested;     

    public GameContext(Spaceship player, EntityManager<Alien> aliens) {
        this.player = player;
        this.aliens = aliens;
        this.bossRequested = false;
    }

    public void freezeAllAliens() {
        for (Alien a : aliens.getEntities()) {
            a.setFrozen(true);
        }
    }
    public void healPlayer(int amount) {
        player.heal(amount);
    }
    public void grantShield(int hits) {
        player.attachShield(new Shield(hits));
    }
    public void unfreezeAllAliens() {
        for (Alien a : aliens.getEntities()) {
            a.setFrozen(false);
        }
    }

    public void wipeAllAliens() {
        for (Alien a : aliens.getEntities()) {
            a.takeHit(9999);
        }
    }

    public void requestBoss() {
        bossRequested = true;
    }

    public boolean isBossRequested() {
        return bossRequested;
    }

    public void clearBossRequest() {
        bossRequested = false;
    }
}
