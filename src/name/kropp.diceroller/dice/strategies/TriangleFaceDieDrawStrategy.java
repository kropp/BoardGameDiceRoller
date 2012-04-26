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
public class TriangleFaceDieDrawStrategy extends BaseDieDrawStrategy {
    @Override
    protected Drawable getDieDrawable(Context context, Die die, int size) {
        Path path = new Path();
        final float sin60 = 0.866025404f;
        float offset = size * (1 - sin60);
        path.moveTo(size / 2, 0);
        path.lineTo(0, size - offset);
        path.lineTo(size, size - offset);
        path.close();
        ShapeDrawable result = new ShapeDrawable(new PathShape(path, size, size));
        result.setBounds(0, 0, size, size);
        setPaint(die, result);
        return result;
    }
}
