package lastorbit.rules;


public abstract class TriggerOption {

    private final String name;
    private final boolean hasValue;
    private final int minValue;
    private final int maxValue;
    private final int defaultValue;

    public TriggerOption(String name, boolean hasValue,
                            int minValue, int maxValue, int defaultValue) {
        this.name = name;
        this.hasValue = hasValue;
        this.minValue = minValue;
        this.maxValue = maxValue;
        this.defaultValue = defaultValue;
    }

    public abstract Trigger build(int value);

    public String getName()     { return name; }
    public boolean hasValue()   { return hasValue; }
    public int getMinValue()    { return minValue; }
    public int getMaxValue()    { return maxValue; }
    public int getDefaultValue(){ return defaultValue; }

    public String describe(int value) {
        return hasValue ? name + " " + value : name;
    }
}