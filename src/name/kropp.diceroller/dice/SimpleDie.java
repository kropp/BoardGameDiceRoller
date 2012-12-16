package name.kropp.diceroller.dice;

import android.content.Context;
import android.view.View;
import name.kropp.diceroller.R;
import name.kropp.diceroller.dice.strategies.DieDrawStrategy;

import java.util.Random;

/**
 * Created by IntelliJ IDEA.
 * User: kropp
 */
public class SimpleDie implements Die {
    protected Random myRandomizer;
    protected int myValue;
    protected int mySides;
    private int myColor;
    private int myDieColor;

    private DieDrawStrategy myDrawStrategy;

    public SimpleDie(int sides, long seed, int dieColor, int color, DieDrawStrategy drawStrategy) {
        mySides = sides;
        myDieColor = dieColor;
        myColor = color;
        myDrawStrategy = drawStrategy;
        myRandomizer = new Random(seed);
    }

    public void roll() {
        myValue = myRandomizer.nextInt(mySides) + 1;
    }

    public int getCurrentValue() {
        return myValue;
    }

    @Override
    public boolean countsInSum() {
        return true;
    }

    @Override
    public String toString() {
        return String.format("<font color=\"#%x\">%d</font>", myDieColor & 0x00ffffff, myValue);
    }

    @Override
    public View getCurrentView(Context context, boolean selected) {
        final int size = (int) context.getResources().getDimension(R.dimen.diesize);

        return myDrawStrategy.draw(this, size, size, selected, context);

/*
        Drawable drawable = getDieDrawable(context);

        final int size = (int) context.getResources().getDimension(R.dimen.diesize);

        Bitmap canvasBitmap = Bitmap.createBitmap(size, size, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(canvasBitmap);

        drawable.setBounds(0, 0, size, size);
        drawable.draw(canvas);

        drawFace(size, size, canvas, context);

        ImageView image = new ImageView(context);
        image.setImageBitmap(canvasBitmap);

        return image;
*/
    }
    public int getDieColor() {
        return myDieColor;
    }

    public int getColor() {
        return myColor;
    }
}
