package name.kropp.diceroller;

import android.content.Context;
import android.graphics.*;
import android.graphics.drawable.GradientDrawable;
import android.view.View;
import android.widget.ImageView;

import java.util.Random;

/**
 * Created by IntelliJ IDEA.
 * User: kropp
 */
public class SimpleDie implements Die {
    private Random myRandomizer;
    private int myValue;
    private int mySides;
    private int myColor;
    private int myDieColor;

    public SimpleDie(int sides, long seed, int dieColor, int color) {
        mySides = sides;
        myDieColor = dieColor;
        myColor = color;
        myRandomizer = new Random(seed);
    }

    public void roll() {
        myValue = myRandomizer.nextInt(mySides) + 1;
    }

    public int getCurrentValue() {
        return myValue;
    }

    @Override
    public String toString() {
        return String.format("<font color=\"#%x\">%d</font>", myDieColor & 0x00ffffff, myValue);
    }

    @Override
    public View getCurrentView(Context context) {
        GradientDrawable drawable = (GradientDrawable) context.getResources().getDrawable(R.drawable.die6);
        drawable.setColor(getDieColor());

        final int width = drawable.getIntrinsicWidth();
        final int height = drawable.getIntrinsicHeight();

        Bitmap canvasBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(canvasBitmap);

        drawable.setBounds(0, 0, width, height);
        drawable.draw(canvas);

        drawFace(width, height, canvas, context);

        ImageView image = new ImageView(context);
        image.setImageBitmap(canvasBitmap);

        return image;
    }

    protected void drawFace(int width, int height, Canvas canvas, Context context) {
        if (mySides == 6)
            drawDots(width, height, canvas);
        else
            drawText(width, height, canvas);
    }

    private void drawDots(int width, int height, Canvas canvas) {
        Paint paint = new Paint();
        canvas.setDrawFilter(new PaintFlagsDrawFilter(0, Paint.ANTI_ALIAS_FLAG | Paint.FILTER_BITMAP_FLAG));
        paint.setColor(getColor());

        final float halfSize = width / 8 - 1;
        float centerX = (float) (width / 2.0);
        float centerY = (float) (height / 2.0);

        if (myValue % 2 == 1)
            drawDot(canvas, paint, halfSize, centerX, centerY);

        if (myValue > 1) {
            drawDot(canvas, paint, halfSize, halfSize * 2, height - halfSize * 2);
            drawDot(canvas, paint, halfSize, width - halfSize * 2, halfSize * 2);
        }

        if (myValue > 3) {
            drawDot(canvas, paint, halfSize, width - halfSize * 2, height - halfSize * 2);
            drawDot(canvas, paint, halfSize, halfSize * 2, halfSize * 2);
        }

        if (myValue == 6) {
            drawDot(canvas, paint, halfSize, halfSize * 2, centerY);
            drawDot(canvas, paint, halfSize, width - halfSize * 2, centerY);
        }
    }

    private void drawDot(Canvas canvas, Paint paint, float halfSize, float x, float y) {
        canvas.drawOval(new RectF(x - halfSize, y - halfSize, x + halfSize, y + halfSize), paint);
    }

    private void drawText(int width, int height, Canvas canvas) {
        Paint paint = new Paint();
        paint.setFilterBitmap(true);
        canvas.setDrawFilter(new PaintFlagsDrawFilter(0, Paint.ANTI_ALIAS_FLAG | Paint.FILTER_BITMAP_FLAG));
        paint.setTextAlign(Paint.Align.CENTER);
        paint.setTextSize(24);
        paint.setFakeBoldText(true);
        paint.setColor(getColor());

        String s = String.valueOf(myValue);
        Rect bounds = new Rect();
        paint.getTextBounds(s, 0, s.length(), bounds);
        canvas.drawText(s, width / 2, (height - (bounds.top - bounds.bottom)) / 2, paint);
    }

    private int getDieColor() {
        return myDieColor;
    }

    public int getColor() {
        return myColor;
    }
}
