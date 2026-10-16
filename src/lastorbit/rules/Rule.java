package lastorbit.rules;


public class Rule {

    private final Trigger trigger;
    private final Effect effect;
    private final boolean oneShot;
    private boolean armed;          

    public Rule(Trigger trigger, Effect effect, boolean oneShot) {
        this.trigger = trigger;
        this.effect = effect;
        this.oneShot = oneShot;
        this.armed = true;
    }

   
    public void evaluate(GameState state, GameContext context) {
        boolean met = trigger.isMet(state);

        if (met && armed) {
            effect.apply(context);
            if (oneShot) {
                armed = false;     
            }
        } else if (!met && !armed) {
            armed = true;         
        }
    }

    public Trigger getTrigger() { return trigger; }
    public Effect getEffect()   { return effect; }

    
    public boolean sameAs(Rule other) {
        return this.trigger.getClass() == other.trigger.getClass()
            && this.effect.getClass() == other.effect.getClass();
    }
}