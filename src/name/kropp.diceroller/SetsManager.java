package name.kropp.diceroller;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: kropp
 */
public class SetsManager {
    private static SetsManager ourInstance;

    private ArrayList<DiceSet> myDiceSets = new ArrayList<DiceSet>();

    private SetsManager() {
    }

    public static SetsManager getInstance()
    {
        if (ourInstance == null)
        {
            ourInstance = new SetsManager();
            PredefinedSets.init(ourInstance);
        }
        return ourInstance;
    }

    public void addSet(DiceSet diceSet) {
        myDiceSets.add(diceSet);
    }

    public List<DiceSet> getSets() {
        return myDiceSets;
    }
}
