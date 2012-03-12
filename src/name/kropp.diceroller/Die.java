package name.kropp.diceroller;

import android.content.Context;
import android.view.View;

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
     * Should add this value to set sum or not.
     * @return true if this die value should be count in set sum, false otherwise
     */
    public boolean countsInSum();

    /**
     * Returns view displaying current die state.
     * @return View
     * @param context Current context
     */
    public View getCurrentView(Context context);
}
