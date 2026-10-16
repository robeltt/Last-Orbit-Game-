package lastorbit.interfaces;

import java.util.List;
import lastorbit.entities.Alien;

public interface MiniSpawner {
    List<Alien> collectPendingChildren();
}