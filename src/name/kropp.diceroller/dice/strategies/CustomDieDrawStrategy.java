package name.kropp.diceroller.dice.strategies;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import name.kropp.diceroller.dice.CustomDie;
import name.kropp.diceroller.dice.Die;

/**
 * Created by IntelliJ IDEA.
 * User: kropp
 */
public class CustomDieDrawStrategy extends RectFaceDieDrawStrategy {
    @Override
    protected void drawFace(int width, int height, Canvas canvas, Die die, Context context) {
        if (die instanceof CustomDie) {
            CustomDie customDie = (CustomDie) die;
            Drawable drawable = context.getResources().getDrawable(customDie.getIconId(context));
            drawable.setBounds(0, 0, width, height);
            drawable.draw(canvas);
        }
    }
}
