package lastorbit.rules.effects;

import lastorbit.rules.Effect;
import lastorbit.rules.GameContext;

public class FreezeEffect implements Effect {

    @Override
    public void apply(GameContext context) {
        context.freezeAllAliens();
    }

    @Override
    public String getName() {
        return "Freeze all aliens";
    }
}
