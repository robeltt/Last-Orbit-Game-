package lastorbit.rules.options;

import lastorbit.rules.Effect;
import lastorbit.rules.EffectOption;
import lastorbit.rules.effects.SpawnBossEffect;

public class SpawnBossOption extends EffectOption {
    public SpawnBossOption() {
        super("Spawn a boss", false, 0, 0, 0);
    }
    @Override
    public Effect build(int value) {
        return new SpawnBossEffect();
    }
}