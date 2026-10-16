package lastorbit.upgrades;

import lastorbit.entities.Alien;
import lastorbit.entities.Spaceship;
import lastorbit.util.EntityManager;


public class UpgradeContext {

    private final Spaceship player;
    private final EntityManager<Alien> aliens;

    public UpgradeContext(Spaceship player, EntityManager<Alien> aliens) {
        this.player = player;
        this.aliens = aliens;
    }

    public Spaceship getPlayer() { return player; }
    public EntityManager<Alien> getAliens() { return aliens; }
}
