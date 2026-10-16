package lastorbit.waves;

import lastorbit.entities.Alien;
import lastorbit.interfaces.MiniFactory;

public class MiniProducer implements MiniFactory {

    private final EnemyFactory factory;
    private final double miniSpeed;

    public MiniProducer(EnemyFactory factory, double miniSpeed) {
        this.factory = factory;
        this.miniSpeed = miniSpeed;
    }

    @Override
    public Alien create(double x, double y) {
        return factory.createMini(x, y, miniSpeed);
    }
}