package lastorbit.upgrades;

public class WeaponUpgrade extends Upgrade {

    public WeaponUpgrade() {
        super(600);   // ~10s
    }

    @Override
    public void apply(UpgradeContext ctx) {
        ctx.getPlayer().setSpreadEnabled(true);
    }

    @Override
    public void expire(UpgradeContext ctx) {
        ctx.getPlayer().setSpreadEnabled(false);
    }

    @Override
    public String getLabel() {
        return "ENHANCED WEAPON";
    }
}