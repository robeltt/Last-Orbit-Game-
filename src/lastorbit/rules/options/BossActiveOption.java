package lastorbit.rules.options;

import lastorbit.rules.Trigger;
import lastorbit.rules.TriggerOption;
import lastorbit.rules.triggers.BossActiveTrigger;

public class BossActiveOption extends TriggerOption {
    public BossActiveOption() {
        super("A boss is on screen", false, 0, 0, 0);
    }
    @Override
    public Trigger build(int value) {
        return new BossActiveTrigger();
    }
}