package lastorbit.upgrades;


public abstract class Upgrade {

    private final int durationFrames;
    private int elapsed;
    private boolean active;

    public Upgrade(int durationFrames) {
        this.durationFrames = durationFrames;
        this.elapsed = 0;
        this.active = false;
    }

    public abstract void apply(UpgradeContext ctx);

    public abstract void expire(UpgradeContext ctx);

    public void tick(UpgradeContext ctx) {
    }

    public void activate(UpgradeContext ctx) {
        active = true;
        apply(ctx);
    }

    public void update(UpgradeContext ctx) {
        if (!active) {
            return;
        }
        tick(ctx);
        elapsed++;
        if (elapsed >= durationFrames) {
            active = false;
            expire(ctx);
        }
    }

    public boolean isActive() {
        return active;
    }

    public int getRemainingFrames() {
        return Math.max(0, durationFrames - elapsed);
    }

    public abstract String getLabel();
}