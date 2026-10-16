package lastorbit.combat;

import lastorbit.entities.EnemyProjectile;
import lastorbit.entities.Spaceship;
import lastorbit.util.EntityManager;


public final class PlayerHitManager {

    private PlayerHitManager() { }

    public static void resolve(EntityManager<EnemyProjectile> enemyShots,
                               Spaceship player) {
        if (player == null) {
            return;
        }
        for (EnemyProjectile bolt : enemyShots.getEntities()) {
            if (!bolt.isAlive()) {
                continue;
            }
            if (bolt.getBounds().intersects(player.getBounds())) {
                player.takeHit(bolt.getDamage());   
                bolt.onHit();
            }
        }
    }
}