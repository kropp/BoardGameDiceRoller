package name.kropp.diceroller;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: kropp
 */
public class SetsManager {
    private static SetsManager ourInstance;

    private SetsManager() {
    }

    public static SetsManager getInstance()
    {
        if (ourInstance == null)
            ourInstance = new SetsManager();
        return ourInstance;
    }
    
    public List<DiceSet> getSets() {
        ArrayList<DiceSet> diceSets = new ArrayList<DiceSet>();
        DiceSet myDiceSet = new DiceSet();

        long seed = System.currentTimeMillis();
        myDiceSet.addDice(new Dice6(seed));
        myDiceSet.addDice(new RedDice6(seed * System.currentTimeMillis()));
        myDiceSet.addDice(new SettlersOfCatanCitiesAndKnightsEventDice(seed * seed * System.currentTimeMillis()));

        diceSets.add(myDiceSet);

        return diceSets;
    }
}
