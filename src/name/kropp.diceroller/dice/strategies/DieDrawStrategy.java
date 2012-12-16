package name.kropp.diceroller.dice.strategies;

import android.content.Context;
import android.view.View;
import name.kropp.diceroller.dice.Die;

/**
 * Created by IntelliJ IDEA.
 * User: kropp
 */
public interface DieDrawStrategy {
    View draw(Die die, int width, int height, boolean selected, Context context);
}
