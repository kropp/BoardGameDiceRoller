package name.kropp.diceroller;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: kropp
 */
public class Game {
    private String myId;
    private String myName;
    private Version myVersion;

    private List<DiceSet> myDiceSets;

    public Game(String id, String name, Version version) {
        myId = id;
        myName = name;
        myVersion = version;
        myDiceSets = new ArrayList<DiceSet>();
    }

    public void addDiceSet(DiceSet set) {
        myDiceSets.add(set);
    }

    public String getId() {
        return myId;
    }

    public String getName() {
        return myName;
    }

    public List<DiceSet> getDiceSets() {
        return myDiceSets;
    }
}
