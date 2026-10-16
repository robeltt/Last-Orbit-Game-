package lastorbit.rules;


public interface Effect {
    void apply(GameContext context);

    String getName();
}
