package name.kropp.diceroller.dice;

import name.kropp.diceroller.Die;
import name.kropp.diceroller.DieDrawStrategy;
import name.kropp.diceroller.SimpleDie;

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
        SimpleDie die = new SimpleDie(mySides, seed, dieColor, color);
        die.setDrawStrategy(myDrawStrategy);
        return die;
    }
}
