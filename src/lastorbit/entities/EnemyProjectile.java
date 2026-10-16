package lastorbit.entities;

import lastorbit.util.Animation;

public class EnemyProjectile extends Projectile {

    private static final double SPEED = -8.0;   
    private static final int DAMAGE = 1;

    public EnemyProjectile(double x, double y, int width, int height,
                           Animation animation, int screenWidth) {
        super(x, y, width, height, animation, SPEED, screenWidth, DAMAGE);
    }
}