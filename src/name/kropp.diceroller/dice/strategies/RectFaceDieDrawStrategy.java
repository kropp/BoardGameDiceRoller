package name.kropp.diceroller.dice.strategies;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import name.kropp.diceroller.dice.Die;
import name.kropp.diceroller.R;

/**
 * Created by IntelliJ IDEA.
 * User: kropp
 */
public class RectFaceDieDrawStrategy extends BaseDieDrawStrategy {
    @Override
    protected Drawable getDieDrawable(Context context, Die die, int size, boolean selected) {
        GradientDrawable drawable = (GradientDrawable) context.getResources().getDrawable(selected ? R.drawable.die6selected : R.drawable.die6);
        drawable.setColor(die.getDieColor());
        return drawable;
    }
}