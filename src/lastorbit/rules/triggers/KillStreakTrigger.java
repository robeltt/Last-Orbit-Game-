package lastorbit.rules.triggers;

import lastorbit.rules.GameState;
import lastorbit.rules.Trigger;

public class KillStreakTrigger implements Trigger {

    private final int target;

    public KillStreakTrigger(int target) {
        this.target = target;
    }

    public int getTarget() {
        return target;
    }

    @Override
    public boolean isMet(GameState state) {
        return state.getKillStreak() >= target;
    }

    @Override
    public String getName() {
        return "Kill streak of " + target;
    }
}