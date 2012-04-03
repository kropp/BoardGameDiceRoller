package name.kropp.diceroller;

import android.content.Context;
import android.graphics.Path;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.PathShape;

/**
 * Created by IntelliJ IDEA.
 * User: kropp
 */
public class PentagonFaceDieDrawStrategy extends BaseDieDrawStrategy {
    @Override
    protected Drawable getDieDrawable(Context context, Die die, int size) {
        Path path = new Path();
        path.moveTo(size / 2, 0);
        path.lineTo(getX(5, 1, size), getY(5, 1, size));
        path.lineTo(getX(5, 2, size), getY(5, 2, size));
        path.lineTo(getX(5, 3, size), getY(5, 3, size));
        path.lineTo(getX(5, 4, size), getY(5, 4, size));
        path.close();
        ShapeDrawable result = new ShapeDrawable(new PathShape(path, size, size));
        result.setBounds(0, 0, size, size);
        setPaint(die, result);
        return result;
    }

    private float getX(int n, int i, float size) {
        return (float) (size / 2 * (1 + Math.cos(-Math.PI / 2 + 2 * Math.PI * i / n)));
    }

    private float getY(int n, int i, float size) {
        return (float) (size / 2 * (1 + Math.sin(-Math.PI / 2 + 2 * Math.PI * i / n)));
    }
}
