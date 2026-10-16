package lastorbit.rules.options;

import lastorbit.rules.Effect;
import lastorbit.rules.EffectOption;
import lastorbit.rules.effects.ScreenWipeEffect;

public class ScreenWipeOption extends EffectOption {
    public ScreenWipeOption() {
        super("Wipe all aliens", false, 0, 0, 0);
    }
    @Override
    public Effect build(int value) {
        return new ScreenWipeEffect();
    }
}
