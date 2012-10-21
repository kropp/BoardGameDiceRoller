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
        return myNumbers[super.getCurrentValue()-1];
    }
}
