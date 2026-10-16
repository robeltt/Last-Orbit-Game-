package lastorbit.rules;


public interface Trigger {
    boolean isMet(GameState state);

    String getName();
}