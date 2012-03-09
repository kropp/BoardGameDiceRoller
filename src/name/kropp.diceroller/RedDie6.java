package name.kropp.diceroller;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

/**
 * Created by IntelliJ IDEA.
 * User: kropp
 */
public class RedDie6 extends SimpleDie {
    public RedDie6(long seed) {
        super(6, seed);
    }

    @Override
    public View getCurrentView(Context context) {
        ImageView image = new ImageView(context);
        image.setImageDrawable(context.getResources().getDrawable(getIconId()));
        return image;
    }

    public int getIconId() {
        switch (getCurrentValue())
        {
            case 1: return R.drawable.r1;
            case 2: return R.drawable.r2;
            case 3: return R.drawable.r3;
            case 4: return R.drawable.r4;
            case 5: return R.drawable.r5;
            case 6: return R.drawable.r6;
            default: return R.drawable.r1;
        }
    }

    @Override
    public String toString() {
        return String.format("<font color=\"red\">%d</font>", getCurrentValue());
    }
}
