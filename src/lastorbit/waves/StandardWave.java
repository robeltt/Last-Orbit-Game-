package lastorbit.waves;

import java.util.Random;

import lastorbit.entities.Alien;

public class StandardWave extends Wave {

    public StandardWave(WaveParameters params, EnemyFactory factory,
                        int screenWidth, int screenHeight, Random random) {
        super(params, factory, screenWidth, screenHeight, random);
    }

    @Override
    protected Alien spawnOne() {
        double x = spawnX();
        double y = randomY();
        double speed = rollSpeed();
        if (rollArmored()) {
            return factory().createArmored(x, y, speed);
        } else {
            return factory().createStandard(x, y, speed);
        }
    }
}