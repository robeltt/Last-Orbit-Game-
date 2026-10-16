package lastorbit.rules.triggers;

import lastorbit.rules.GameState;
import lastorbit.rules.Trigger;

public class LowHealthTrigger implements Trigger {

    private final int threshold;

    public LowHealthTrigger(int threshold) {
        this.threshold = threshold;
    }

    @Override
    public boolean isMet(GameState state) {
        return state.getPlayerHealth() <= threshold;
    }

    @Override
    public String getName() {
        return "Health drops to " + threshold;
    }
}