package lastorbit.rules.options;

import lastorbit.rules.Effect;
import lastorbit.rules.EffectOption;
import lastorbit.rules.effects.HealEffect;

public class HealOption extends EffectOption {
    public HealOption() {
        super("Heal health", true, 1, 5, 2);
    }
    @Override
    public Effect build(int value) {
        return new HealEffect(value);
    }
}
