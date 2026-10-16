package lastorbit.rules.effects;

import lastorbit.rules.Effect;
import lastorbit.rules.GameContext;

public class HealEffect implements Effect {

    private final int amount;

    public HealEffect(int amount) {
        this.amount = amount;
    }

    @Override
    public void apply(GameContext context) {
        context.healPlayer(amount);
    }

    @Override
    public String getName() {
        return "Heal " + amount + " health";
    }
}