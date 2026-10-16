package lastorbit.entities;

import lastorbit.util.Animation;

public class MiniAlien extends Alien {

    private final Animation animation;

    public MiniAlien(double x, double y, int width, int height,
                     Animation animation, double speed, int earthLine,
                     int screenRight) {
        super(x, y, width, height, 1, speed, earthLine, screenRight);
        this.animation = animation;
    }

    @Override
    protected Animation activeAnimation() {
        return animation;
    }
}