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
     * Returns view displaying current die state.
     * @return View
     * @param context Current context
     */
    public View getCurrentView(Context context);
}
