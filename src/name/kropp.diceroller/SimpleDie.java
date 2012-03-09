package name.kropp.diceroller;

import android.content.Context;
import android.graphics.*;
import android.graphics.drawable.BitmapDrawable;
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
        if (myDieColor == 0xffff0000)
            return String.format("<font color=\"red\">%d</font>", myValue);
        return String.valueOf(myValue);
    }

    @Override
    public View getCurrentView(Context context) {
        GradientDrawable drawable = (GradientDrawable) context.getResources().getDrawable(R.drawable.die6);
        drawable.setColor(getDieColor());
        
        final int width = drawable.getIntrinsicWidth();
        final int height = drawable.getIntrinsicHeight();

        Bitmap canvasBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(canvasBitmap);

        Paint paint = new Paint();
        paint.setFilterBitmap(true);
        canvas.setDrawFilter(new PaintFlagsDrawFilter(0, Paint.ANTI_ALIAS_FLAG|Paint.FILTER_BITMAP_FLAG));
        paint.setTextAlign(Paint.Align.CENTER);
        paint.setTextSize(24);
        paint.setFakeBoldText(true);
        paint.setColor(getColor());

        drawable.setBounds(0, 0, width, height);
        drawable.draw(canvas);
        String s = String.valueOf(myValue);
        Rect bounds = new Rect();
        paint.getTextBounds(s, 0, s.length(), bounds);
        canvas.drawText(s, width / 2, (height - (bounds.top - bounds.bottom)) / 2, paint);

        ImageView image = new ImageView(context);
        image.setImageBitmap(canvasBitmap);

        return image;
    }

    private int getDieColor() {
        return myDieColor;
    }

    public int getColor() {
        return myColor;
    }
}
