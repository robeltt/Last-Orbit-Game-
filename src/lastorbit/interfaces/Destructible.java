package lastorbit.interfaces;

public interface Destructible {
    void takeHit(int damage);
    boolean isDestroyed();
}