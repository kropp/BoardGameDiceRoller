package name.kropp.diceroller;

import android.content.Context;
import android.graphics.*;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.PathShape;
import android.view.View;
import android.widget.ImageView;

/**
 * Created by IntelliJ IDEA.
 * User: kropp
 */
public class BaseDieDrawStrategy implements DieDrawStrategy {
    @Override
    public View draw(Die die, int size, int height, Context context) {
        Drawable drawable = getDieDrawable(context, die);

        Bitmap canvasBitmap = Bitmap.createBitmap(size, size, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(canvasBitmap);

        drawable.setBounds(0, 0, size, size);
        drawable.draw(canvas);

        drawFace(size, size, canvas, die);

        ImageView image = new ImageView(context);
        image.setImageBitmap(canvasBitmap);

        return image;
    }

    private Drawable getDieDrawable(Context context, Die die) {
        if (true /*mySides == 6*/) {
            GradientDrawable drawable = (GradientDrawable) context.getResources().getDrawable(R.drawable.die6);
            drawable.setColor(die.getDieColor());
            return drawable;
        } else {
            float size = context.getResources().getDimension(R.dimen.diesize);
            Path path = new Path();
//            if (mySides == 10) {
                // kite
                path.moveTo(size / 2, 0);
                path.lineTo(0.22f * size, 0.55f * size);
                path.lineTo(size / 2, 0.8f * size);
                path.lineTo(0.78f * size, 0.55f * size);
//            } else if (mySides == 12) {
                // pentagon
                path.moveTo(size / 2, 0);
                path.lineTo(getX(5, 1, size), getY(5, 1, size));
                path.lineTo(getX(5, 2, size), getY(5, 2, size));
                path.lineTo(getX(5, 3, size), getY(5, 3, size));
                path.lineTo(getX(5, 4, size), getY(5, 4, size));
//            } else {
                // triangle
                final float sin60 = 0.866025404f;
                float offset = size * (1 - sin60);
                path.moveTo(size / 2, 0);
                path.lineTo(0, size - offset);
                path.lineTo(size, size - offset);
//            }
            path.close();
            ShapeDrawable result = new ShapeDrawable(new PathShape(path, size, size));
            result.setBounds(0, 0, (int) size, (int) size);
            Paint paint = result.getPaint();
            paint.setColor(die.getDieColor());
            paint.setStrokeWidth(2);
            paint.setStrokeJoin(Paint.Join.ROUND);
            return result;
        }
    }

    private float getX(int n, int i, float size) {
        return (float) (size / 2 * (1 + Math.cos(-Math.PI / 2 + 2 * Math.PI * i / n)));
    }

    private float getY(int n, int i, float size) {
        return (float) (size / 2 * (1 + Math.sin(-Math.PI / 2 + 2 * Math.PI * i / n)));
    }

    protected void drawFace(int width, int height, Canvas canvas, Die die) {
        //if (mySides == 6)
            drawDots(die, width, height, canvas);
        //else
            drawText(die.getCurrentValue(), width, height, die.getColor(), canvas);
    }

    private void drawDots(Die die, int width, int height, Canvas canvas) {
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

    private void drawText(int value, int width, int height, int color, Canvas canvas) {
        Paint paint = new Paint();
        paint.setFilterBitmap(true);
        canvas.setDrawFilter(new PaintFlagsDrawFilter(0, Paint.ANTI_ALIAS_FLAG | Paint.FILTER_BITMAP_FLAG));
        paint.setTextAlign(Paint.Align.CENTER);
        paint.setTextSize(24);
        paint.setFakeBoldText(true);
        paint.setColor(color);

        String s = String.valueOf(value);
        Rect bounds = new Rect();
        paint.getTextBounds(s, 0, s.length(), bounds);
        canvas.drawText(s, width / 2, (height - (bounds.top - bounds.bottom)) / 2, paint);
    }
}
