package lastorbit.components;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import lastorbit.entities.PlayerProjectile;

public class StandardWeapon extends Weapon {

    public StandardWeapon(int screenWidth, BufferedImage[] boltFrames) {
        super(screenWidth, boltFrames);
    }

    @Override
    public List<PlayerProjectile> fire(int spreadCount, double x, double y) {
        List<PlayerProjectile> shots = new ArrayList<>();
        shots.add(fire(x, y));
        return shots;
    }
}