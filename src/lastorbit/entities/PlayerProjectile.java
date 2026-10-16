package lastorbit.entities;

import lastorbit.util.Animation;

public class PlayerProjectile extends Projectile {

    private static final double SPEED = 12.0;
    private static final int DAMAGE = 1;

    public PlayerProjectile(double x, double y, int width, int height,
                            Animation animation, int screenWidth) {
        super(x, y, width, height, animation, SPEED, screenWidth, DAMAGE);
    }
}