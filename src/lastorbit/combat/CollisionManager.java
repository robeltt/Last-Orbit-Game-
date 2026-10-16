package lastorbit.combat;

import lastorbit.entities.Alien;
import lastorbit.entities.PlayerProjectile;
import lastorbit.util.EntityManager;



public final class CollisionManager {

    private CollisionManager() { }

    public static int resolve(EntityManager<PlayerProjectile> projectiles,
                              EntityManager<Alien> aliens) {
        int destroyed = 0;

        for (PlayerProjectile bolt : projectiles.getEntities()) {
            if (!bolt.isAlive()) {
                continue;
            }
            for (Alien alien : aliens.getEntities()) {
                if (!alien.isAlive()) {
                    continue;
                }
                if (bolt.getBounds().intersects(alien.getBounds())) {
                    alien.takeHit(bolt.getDamage());   
                    bolt.onHit();                     
                    if (alien.isDestroyed()) {
                        destroyed++;
                    }
                    break;
                }
            }
        }
        return destroyed;
    }
}