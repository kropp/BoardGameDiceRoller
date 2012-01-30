package name.kropp.diceroller;

/**
 * Created by IntelliJ IDEA.
 * User: kropp
 */
public interface Die {
    /**
     * Rolls dice.
     */
    public void roll();

    /**
     * Current value. Must be called only after roll()
     * @return current value
     */
    public int getCurrentValue();

    /**
     * Returns resource icon id.
     * @return icon id
     */
    public int getIconId();
}
