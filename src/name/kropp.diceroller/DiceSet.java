package name.kropp.diceroller;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: kropp
 */
public class DiceSet {
    private final List<Dice> myDices = new LinkedList<Dice>();

    public String getName() {
        return "Settlers of Catan: Cities & Knights";
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