package name.kropp.diceroller;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PaintFlagsDrawFilter;
import android.graphics.RectF;

/**
 * Created by IntelliJ IDEA.
 * User: kropp
 */
public class CubicDieDrawStrategy extends RectFaceDieDrawStrategy {
    @Override
    protected void drawFace(int width, int height, Canvas canvas, Die die, Context context) {
        Paint paint = new Paint();
        canvas.setDrawFilter(new PaintFlagsDrawFilter(0, Paint.ANTI_ALIAS_FLAG | Paint.FILTER_BITMAP_FLAG));
        paint.setColor(die.getColor());

        final float halfSize = width / 8 - 1;
        float centerX = (float) (width / 2.0);
        float centerY = (float) (height / 2.0);

        int currentValue = die.getCurrentValue();

        if (currentValue % 2 == 1)
            drawDot(canvas, paint, halfSize, centerX, centerY);

        if (currentValue > 1) {
            drawDot(canvas, paint, halfSize, halfSize * 2, height - halfSize * 2);
            drawDot(canvas, paint, halfSize, width - halfSize * 2, halfSize * 2);
        }

        if (currentValue > 3) {
            drawDot(canvas, paint, halfSize, width - halfSize * 2, height - halfSize * 2);
            drawDot(canvas, paint, halfSize, halfSize * 2, halfSize * 2);
        }

        if (currentValue == 6) {
            drawDot(canvas, paint, halfSize, halfSize * 2, centerY);
            drawDot(canvas, paint, halfSize, width - halfSize * 2, centerY);
        }
    }

    private void drawDot(Canvas canvas, Paint paint, float halfSize, float x, float y) {
        canvas.drawOval(new RectF(x - halfSize, y - halfSize, x + halfSize, y + halfSize), paint);
    }
}