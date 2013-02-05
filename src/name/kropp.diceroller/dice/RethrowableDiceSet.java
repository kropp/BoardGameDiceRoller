package name.kropp.diceroller.dice;

import name.kropp.diceroller.dice.sum.DiceSumNotificationMaker;

import java.util.HashSet;

/**
 * Created by IntelliJ IDEA.
 * User: kropp
 */
public class RethrowableDiceSet extends DiceSet {
    private final int myAttempts;
    private final HashSet<Die> myCurrentSelection = new HashSet<Die>();

    private int myCurrentAttempts = 0;

    public RethrowableDiceSet(String id, String name, int attempts, DiceSumNotificationMaker notificationMaker) {
        super(id, name, notificationMaker);
        myAttempts = attempts;
    }

    public boolean toggle(Die die) {
        if (isSelected(die)) {
            myCurrentSelection.remove(die);
            return false;
        } else {
            myCurrentSelection.add(die);
            return true;
        }
    }

    public boolean isSelected(Die die) {
        return myCurrentSelection.contains(die);
    }

    public int getLeftAttempts() {
        return myAttempts - myCurrentAttempts;
    }

    public boolean canRethrow() {
        return myCurrentAttempts < myAttempts;
    }

    public void rethrow() {
        if (canRethrow())
            for (Die die : getDice()) {
                if (!isSelected(die))
                    die.roll();
            }
        myCurrentAttempts++;
    }

    @Override
    public void rollAll() {
        myCurrentAttempts = 0;
        myCurrentSelection.clear();
        super.rollAll();
    }
}
