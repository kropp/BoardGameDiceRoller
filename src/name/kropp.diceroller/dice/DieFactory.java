package name.kropp.diceroller.dice;

import name.kropp.diceroller.dice.strategies.DieDrawStrategy;

/**
 * Created by IntelliJ IDEA.
 * User: kropp
 */
public interface DieFactory {
    Die createDie(long seed, int dieColor, int color);

    DieDrawStrategy getDrawStrategy();
}
