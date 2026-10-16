package lastorbit.interfaces;

import lastorbit.entities.Alien;

public interface MiniFactory {
    Alien create(double x, double y);
}