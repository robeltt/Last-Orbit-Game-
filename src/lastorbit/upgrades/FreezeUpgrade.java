package lastorbit.upgrades;

import lastorbit.entities.Alien;

public class FreezeUpgrade extends Upgrade {

    public FreezeUpgrade() {
        super(360);  
    }

    @Override
    public void apply(UpgradeContext ctx) {
        for (Alien alien : ctx.getAliens().getEntities()) {
            alien.setFrozen(true);
        }
    }

    @Override
    public void tick(UpgradeContext ctx) {// to freeze newly spawned aliens
        for (Alien alien : ctx.getAliens().getEntities()) {
            alien.setFrozen(true);
        }
    }

    @Override
    public void expire(UpgradeContext ctx) {
        for (Alien alien : ctx.getAliens().getEntities()) {
            alien.setFrozen(false);
        }
    }

    @Override
    public String getLabel() {
        return "ALIEN FREEZE";
    }
}