package lastorbit.rules.options;

import lastorbit.rules.Effect;
import lastorbit.rules.EffectOption;
import lastorbit.rules.effects.ShieldEffect;

public class ShieldOption extends EffectOption {
    public ShieldOption() {
        super("Gain a shield (hits)", true, 1, 8, 5);
    }
    @Override
    public Effect build(int value) {
        return new ShieldEffect(value);
    }
}