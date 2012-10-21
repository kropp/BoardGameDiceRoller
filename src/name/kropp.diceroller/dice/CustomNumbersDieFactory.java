package name.kropp.diceroller.dice;

import name.kropp.diceroller.dice.strategies.DieDrawStrategy;

/**
 * Created by IntelliJ IDEA.
 * User: kropp
 */
public class CustomNumbersDieFactory implements DieFactory {
    private int[] myNumbers;
    private DieDrawStrategy myDrawStrategy;

    public CustomNumbersDieFactory(int[] myNumbers, DieDrawStrategy myDrawStrategy) {
        this.myNumbers = myNumbers;
        this.myDrawStrategy = myDrawStrategy;
    }

    @Override
    public Die createDie(long seed, int dieColor, int color) {
        return new CustomNumbersDie(myNumbers, seed, dieColor, color, myDrawStrategy);
    }

    @Override
    public DieDrawStrategy getDrawStrategy() {
        return myDrawStrategy;
    }
}
