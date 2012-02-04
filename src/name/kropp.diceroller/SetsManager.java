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
    private int mySelected;

    private SetsManager() {
    }

    public static SetsManager getInstance() {
        if (ourInstance == null) {
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

    public void setSelected(int index) {
        if (0 <= index && index < myDiceSets.size())
            mySelected = index;
    }

    public int getSelected() {
        return mySelected;
    }

    public DiceSet getSelectedSet() {
        return myDiceSets.get(mySelected);
    }

    public void setSelected(String id) {
        int i = 0;
        for (DiceSet diceSet : myDiceSets) {
            if (diceSet.getId().equals(id))
            {
                setSelected(i);
            }
            i++;
        }
    }
}
