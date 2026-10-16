package lastorbit.rules.options;

import lastorbit.rules.Trigger;
import lastorbit.rules.TriggerOption;
import lastorbit.rules.triggers.HalfwayCrossTrigger;

public class HalfwayCrossOption extends TriggerOption {
    public HalfwayCrossOption() {
        super("Alien crosses halfway", false, 0, 0, 0);
    }
    @Override
    public Trigger build(int value) {
        return new HalfwayCrossTrigger();
    }
}
