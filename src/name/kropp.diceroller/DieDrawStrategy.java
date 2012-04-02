package name.kropp.diceroller;

import android.content.Context;
import android.view.View;

/**
 * Created by IntelliJ IDEA.
 * User: kropp
 */
public interface DieDrawStrategy {
    View draw(Die die, int width, int height, Context context);
}
