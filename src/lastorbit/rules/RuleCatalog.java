package lastorbit.rules;

import java.util.ArrayList;
import java.util.List;

import lastorbit.rules.options.KillStreakOption;
import lastorbit.rules.options.LowHealthOption;
import lastorbit.rules.options.HalfwayCrossOption;
import lastorbit.rules.options.BossActiveOption;
import lastorbit.rules.options.ShieldOption;
import lastorbit.rules.options.HealOption;
import lastorbit.rules.options.FreezeOption;
import lastorbit.rules.options.ScreenWipeOption;
import lastorbit.rules.options.SpawnBossOption;

public class RuleCatalog {

    private final List<TriggerOption> triggers;
    private final List<EffectOption> effects;

    public RuleCatalog() {
        this.triggers = new ArrayList<>();
        this.effects = new ArrayList<>();
        registerTriggers();
        registerEffects();
    }

    private void registerTriggers() {
        triggers.add(new KillStreakOption());
        triggers.add(new LowHealthOption());
        triggers.add(new HalfwayCrossOption());
        triggers.add(new BossActiveOption());
    }

    private void registerEffects() {
        effects.add(new ShieldOption());
        effects.add(new HealOption());
        effects.add(new FreezeOption());
        effects.add(new ScreenWipeOption());
        effects.add(new SpawnBossOption());
    }

    public List<TriggerOption> getTriggers() {
        return triggers;
    }

    public List<EffectOption> getEffects() {
        return effects;
    }
}