package lastorbit.rules.triggers;

import lastorbit.rules.GameState;
import lastorbit.rules.Trigger;

public class BossActiveTrigger implements Trigger {

    @Override
    public boolean isMet(GameState state) {
        return state.isBossActive();
    }

    @Override
    public String getName() {
        return "A boss is on screen";
    }
}