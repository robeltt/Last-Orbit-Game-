package lastorbit.rules.effects;

import lastorbit.rules.Effect;
import lastorbit.rules.GameContext;

public class SpawnBossEffect implements Effect {

    @Override
    public void apply(GameContext context) {
        context.requestBoss();
    }

    @Override
    public String getName() {
        return "Spawn a boss immediately";
    }
}