package name.kropp.diceroller.dice;

import name.kropp.diceroller.Die;
import name.kropp.diceroller.SimpleDie;

/**
 * Created by IntelliJ IDEA.
 * User: kropp
 */
public class SimpleDieFactory implements DieFactory {
    private int mySides;

    public SimpleDieFactory(int sides) {
        mySides = sides;
    }

    @Override
    public Die createDie(long seed, int dieColor, int color) {
        return new SimpleDie(mySides, seed, dieColor, color);
    }
}
