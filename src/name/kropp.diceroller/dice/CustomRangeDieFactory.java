package name.kropp.diceroller.dice;

import name.kropp.diceroller.dice.strategies.DieDrawStrategy;

/**
 * Created by IntelliJ IDEA.
 * User: kropp
 */
public class CustomRangeDieFactory implements DieFactory {
    private int myFrom;
    private int myTo;
    private DieDrawStrategy myDrawStrategy;

    public CustomRangeDieFactory(int from, int to, DieDrawStrategy drawStrategy) {
        myFrom = from;
        myTo = to;
        myDrawStrategy = drawStrategy;
    }

    @Override
    public Die createDie(long seed, int dieColor, int color) {
        return new CustomRangeDie(myFrom, myTo, seed, dieColor, color, myDrawStrategy);
    }

    @Override
    public DieDrawStrategy getDrawStrategy() {
        return myDrawStrategy;
    }
}