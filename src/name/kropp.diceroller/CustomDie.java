package name.kropp.diceroller;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;

/**
 * Created by IntelliJ IDEA.
 * User: kropp
 */
public class CustomDie extends SimpleDie {
    private final String[] myFaceImages;

    public CustomDie(int sides, long seed, int dieColor, int color, String[] faceImages) {
        super(sides, seed, dieColor, color);
        myFaceImages = faceImages;
    }

    @Override
    public boolean countsInSum() {
        return false;
    }

    @Override
    protected void drawFace(int width, int height, Canvas canvas, Context context) {
        Drawable drawable = context.getResources().getDrawable(getIconId(context));
        drawable.setBounds(0, 0, width, height);
        drawable.draw(canvas);
    }

    public int getIconId(Context context) {
        return context.getResources().getIdentifier(myFaceImages[getCurrentValue()-1], "drawable", context.getPackageName());
    }

    @Override
    public String toString() {
        return String.format("<img src=\"%s\">", myFaceImages[getCurrentValue()-1]);
    }
}