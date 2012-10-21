package name.kropp.diceroller.dice;

import name.kropp.diceroller.dice.strategies.DieDrawStrategy;

/**
 * Created by IntelliJ IDEA.
 * User: kropp
 */
public class CustomNumbersDie extends SimpleDie {
    private int[] myNumbers;

    public CustomNumbersDie(int[] numbers, long seed, int dieColor, int color, DieDrawStrategy drawStrategy) {
        super(numbers.length, seed, dieColor, color, drawStrategy);
        myNumbers = numbers;
    }

    @Override
    public int getCurrentValue() {
        int i = super.getCurrentValue() - 1;
        if (i >= myNumbers.length)
            i = 0;
        return myNumbers[i];
    }
}
