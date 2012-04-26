package name.kropp.diceroller.dice.strategies;

import android.content.Context;
import android.graphics.Path;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.PathShape;
import name.kropp.diceroller.dice.Die;

/**
 * Created by IntelliJ IDEA.
 * User: kropp
 */
public class KiteFaceDieDrawStrategy extends BaseDieDrawStrategy {
    @Override
    protected Drawable getDieDrawable(Context context, Die die, int size) {
        Path path = new Path();
        path.moveTo(size / 2, 0);
        path.lineTo(0.22f * size, 0.55f * size);
        path.lineTo(size / 2, 0.8f * size);
        path.lineTo(0.78f * size, 0.55f * size);
        path.close();
        ShapeDrawable result = new ShapeDrawable(new PathShape(path, size, size));
        result.setBounds(0, 0, size, size);
        setPaint(die, result);
        return result;
    }

}
