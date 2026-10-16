package lastorbit.interfaces;

import java.util.List;
import lastorbit.entities.EnemyProjectile;


public interface Shooter {
    List<EnemyProjectile> collectPendingShots();//enemy projectile since the player has weapon!
}