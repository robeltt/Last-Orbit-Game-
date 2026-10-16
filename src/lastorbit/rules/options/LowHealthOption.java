package lastorbit.rules.options;

import lastorbit.rules.Trigger;
import lastorbit.rules.TriggerOption;
import lastorbit.rules.triggers.LowHealthTrigger;

public class LowHealthOption extends TriggerOption {
    public LowHealthOption() {
        super("Health drops to", true, 1, 4, 2);
    }
    @Override
    public Trigger build(int value) {
        return new LowHealthTrigger(value);
    }
}
