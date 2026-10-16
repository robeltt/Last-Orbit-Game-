package lastorbit.rules.triggers;

import lastorbit.rules.GameState;
import lastorbit.rules.Trigger;

public class HalfwayCrossTrigger implements Trigger {

    @Override
    public boolean isMet(GameState state) {
        return state.getNearestAlienX() <= state.getScreenWidth() / 2.0;
    }

    @Override
    public String getName() {
        return "Alien crosses the halfway point";
    }
}