package name.kropp.diceroller;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: kropp
 */
public class DiceSet {
    private final List<Dice> myDices = new LinkedList<Dice>();
    private String myName;

    public DiceSet(String name) {
        myName = name;
    }

    public String getName() {
        return myName;
    }

    public void addDice(Dice dice) {
        myDices.add(dice);
    }

    public void rollAll() {
        for (Dice dice : myDices) {
            dice.roll();
        }
    }

    public List<Dice> getDices() {
        return myDices;
    }
}