package lastorbit.rules;

import java.util.ArrayList;
import java.util.List;

import lastorbit.exceptions.InvalidRuleException;
import lastorbit.rules.triggers.KillStreakTrigger;


public class RuleEngine {

    private final List<Rule> rules;

    public RuleEngine() {
        this.rules = new ArrayList<>();
    }

   
    public void addRule(Rule rule) throws InvalidRuleException {
        for (Rule existing : rules) {
            if (existing.sameAs(rule)) {
                throw new InvalidRuleException(
                    "Duplicate rule: that trigger and effect pair is already set.");
            }
        }
        rules.add(rule);
    }

    public void evaluateAll(GameState state, GameContext context) {
        for (Rule rule : rules) {
            rule.evaluate(state, context);
        }
    }


    public int getStreakThreshold() {
        for (Rule rule : rules) {
            Trigger t = rule.getTrigger();
            if (t.getClass() == KillStreakTrigger.class) {
                return ((KillStreakTrigger) t).getTarget();
            }
        }
        return -1;   
    }

    public int getRuleCount() {
        return rules.size();
    }

    public List<Rule> getRules() {
        return rules;
    }
}