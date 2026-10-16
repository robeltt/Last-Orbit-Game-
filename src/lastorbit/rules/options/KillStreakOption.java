package lastorbit.rules.options;

import lastorbit.rules.Trigger;
import lastorbit.rules.TriggerOption;
import lastorbit.rules.triggers.KillStreakTrigger;

public class KillStreakOption extends TriggerOption {
    public KillStreakOption() {
        super("Kill streak of", true, 3, 20, 8);
    }
    @Override
    public Trigger build(int value) {
        return new KillStreakTrigger(value);
    }
}