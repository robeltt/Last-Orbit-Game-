package lastorbit.entities;

import java.awt.image.BufferedImage;
import java.util.Arrays;

import lastorbit.util.Animation;

public class ArmoredAlien extends Alien {

    private static final int HEALTH = 3;

    private final Animation[] states;

    public ArmoredAlien(double x, double y, int width, int height,
                        BufferedImage[] intactFrames, BufferedImage[] damagedFrames,
                        double speed, int earthLine, int screenRight) {
        super(x, y, width, height, HEALTH, speed, earthLine, screenRight);

        int half = damagedFrames.length / 2;
        BufferedImage[] damagedHalf  = Arrays.copyOfRange(damagedFrames, 0, half);
        BufferedImage[] criticalHalf = Arrays.copyOfRange(damagedFrames, half, damagedFrames.length);

        this.states = new Animation[] {
            new Animation(intactFrames, 8),
            new Animation(damagedHalf, 8),
            new Animation(criticalHalf, 8)
        };
    }

    @Override
    protected Animation activeAnimation() {
        double ratio = getHealthRatio();
        int index = (int) ((1.0 - ratio) * states.length);
        if (index < 0) { index = 0; }
        if (index >= states.length) { index = states.length - 1; }
        return states[index];
    }
}