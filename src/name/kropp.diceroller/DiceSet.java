package name.kropp.diceroller;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: kropp
 */
public class DiceSet {
    private final List<Die> myDices = new LinkedList<Die>();
    
    private String myId;
    private String myName;

    public DiceSet(String id, String name) {
        myId = id;
        myName = name;
    }

    public String getId() {
        return myId;
    }

    public String getName() {
        return myName;
    }

    public void addDice(Die die) {
        myDices.add(die);
    }

    public void rollAll() {
        for (Die die : myDices) {
            die.roll();
        }
    }

    public List<Die> getDice() {
        return myDices;
    }

    public int getSum() {
        int result = 0;
        for (Die die : myDices) {
            if (!(die instanceof SettlersOfCatanCitiesAndKnightsEventDie))
            {
                result += die.getCurrentValue();
            }
        }
        return result;
    }
}