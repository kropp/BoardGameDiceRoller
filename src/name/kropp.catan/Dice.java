package name.kropp.catan;

import java.util.Random;

/**
 * Created by IntelliJ IDEA.
 * User: kropp
 */
public class Dice {
    private Random myRandomizer;

    public Dice(long seed) {
        myRandomizer = new Random(seed);
    }

    public int roll() {
        return myRandomizer.nextInt(6) + 1;
    }
}
