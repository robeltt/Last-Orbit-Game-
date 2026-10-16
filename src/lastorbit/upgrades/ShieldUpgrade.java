package lastorbit.upgrades;

import lastorbit.components.Shield;

public class ShieldUpgrade extends Upgrade {

    private static final int SHIELD_HITS = 5;

    public ShieldUpgrade() {
        super(900);  
    }

    @Override
    public void apply(UpgradeContext ctx) {
        ctx.getPlayer().attachShield(new Shield(SHIELD_HITS));
    }

    @Override
    public void expire(UpgradeContext ctx) {
        ctx.getPlayer().detachShield();
    }

    @Override
    public String getLabel() {
        return "SHIELD";
    }
}
