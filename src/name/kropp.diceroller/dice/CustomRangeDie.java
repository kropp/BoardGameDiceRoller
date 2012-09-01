package name.kropp.diceroller.dice;

import name.kropp.diceroller.dice.strategies.DieDrawStrategy;

/**
 * Created by IntelliJ IDEA.
 * User: kropp
 */
public class CustomRangeDie extends SimpleDie {
    private final int myFrom;

    public CustomRangeDie(int from, int to, long seed, int dieColor, int color, DieDrawStrategy drawStrategy) {
        super(to-from, seed, dieColor, color, drawStrategy);
        myFrom = from;
    }

    @Override
    public void roll() {
        myValue = myRandomizer.nextInt(mySides) + myFrom;
    }
}
