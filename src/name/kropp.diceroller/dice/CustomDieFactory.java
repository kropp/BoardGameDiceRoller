package name.kropp.diceroller.dice;

import name.kropp.diceroller.Die;
import name.kropp.diceroller.SettlersOfCatanCitiesAndKnightsEventDie;

/**
 * Created by IntelliJ IDEA.
 * User: kropp
 */
public class CustomDieFactory implements DieFactory {
    @Override
    public Die createDie(long seed, int dieColor, int color) {
        return new SettlersOfCatanCitiesAndKnightsEventDie(seed);
    }
}
