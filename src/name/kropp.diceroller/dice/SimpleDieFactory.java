package name.kropp.diceroller.dice;

import name.kropp.diceroller.dice.strategies.DieDrawStrategy;

/**
 * Created by IntelliJ IDEA.
 * User: kropp
 */
public class SimpleDieFactory implements DieFactory {
    private int mySides;
    private DieDrawStrategy myDrawStrategy;

    public SimpleDieFactory(int sides, DieDrawStrategy drawStrategy) {
        mySides = sides;
        myDrawStrategy = drawStrategy;
    }

    @Override
    public Die createDie(long seed, int dieColor, int color) {
        return new SimpleDie(mySides, seed, dieColor, color, myDrawStrategy);
    }
}
