package lastorbit.components;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import lastorbit.entities.PlayerProjectile;

public class SpreadWeapon extends Weapon {

    public SpreadWeapon(int screenWidth, BufferedImage[] boltFrames) {
        super(screenWidth, boltFrames);
    }

    @Override
    public List<PlayerProjectile> fire(int spreadCount, double x, double y) {
        List<PlayerProjectile> shots = new ArrayList<>();
        double[] offsets = {0, -18, 18};
        for (int i = 0; i < spreadCount && i < offsets.length; i++) {
            shots.add(fire(x, y + offsets[i]));
        }
        if (shots.isEmpty()) {
            shots.add(fire(x, y));//if spreadCount is 0, fire a single shot straight ahead
        }
        return shots;
    }
}