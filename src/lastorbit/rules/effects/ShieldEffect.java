package lastorbit.rules.effects;

import lastorbit.rules.Effect;
import lastorbit.rules.GameContext;

public class ShieldEffect implements Effect {

    private final int hits;

    public ShieldEffect(int hits) {
        this.hits = hits;
    }

    @Override
    public void apply(GameContext context) {
        context.grantShield(hits);
    }

    @Override
    public String getName() {
        return "Gain a shield (" + hits + " hits)";
    }
}