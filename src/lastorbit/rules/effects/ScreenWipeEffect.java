package lastorbit.rules.effects;

import lastorbit.rules.Effect;
import lastorbit.rules.GameContext;

public class ScreenWipeEffect implements Effect {

    @Override
    public void apply(GameContext context) {
        context.wipeAllAliens();
    }

    @Override
    public String getName() {
        return "Wipe all aliens on screen";
    }
}