package lastorbit.rules.options;

import lastorbit.rules.Effect;
import lastorbit.rules.EffectOption;
import lastorbit.rules.effects.FreezeEffect;

public class FreezeOption extends EffectOption {
    public FreezeOption() {
        super("Freeze all aliens", false, 0, 0, 0);
    }
    @Override
    public Effect build(int value) {
        return new FreezeEffect();
    }
}