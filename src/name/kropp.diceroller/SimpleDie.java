package name.kropp.diceroller;

import android.content.Context;
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

    public SimpleDie(int sides, long seed) {
        mySides = sides;
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
        return String.valueOf(myValue);
    }

    @Override
    public View getCurrentView(Context context) {
        ImageView image = new ImageView(context);
        image.setImageResource(getIconId());
        image.setPadding(5, 5, 5, 5);
        return image;
    }

    public int getIconId() {
        switch (myValue) {
            case 1:
                return R.drawable.w1;
            case 2:
                return R.drawable.w2;
            case 3:
                return R.drawable.w3;
            case 4:
                return R.drawable.w4;
            case 5:
                return R.drawable.w5;
            case 6:
                return R.drawable.w6;
            default:
                return R.drawable.w1;
        }
    }
}
