package name.kropp.diceroller;

import java.util.Random;

/**
 * Created by IntelliJ IDEA.
 * User: kropp
 */
public class Die6 implements Die {
    private Random myRandomizer;
    private int myValue;
    
    public Die6(long seed) {
        myRandomizer = new Random(seed);
    }

    public void roll() {
        myValue = myRandomizer.nextInt(6) + 1;
    }

    public int getCurrentValue() {
        return myValue;
    }

    public int getIconId() {
        switch (myValue)
        {
            case 1: return R.drawable.w1;
            case 2: return R.drawable.w2;
            case 3: return R.drawable.w3;
            case 4: return R.drawable.w4;
            case 5: return R.drawable.w5;
            case 6: return R.drawable.w6;
            default: return R.drawable.w1;
        }
    }
}
