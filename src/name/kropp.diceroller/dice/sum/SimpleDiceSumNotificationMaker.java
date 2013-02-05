package name.kropp.diceroller.dice.sum;

import name.kropp.diceroller.dice.DiceSet;

/**
 * Created by IntelliJ IDEA.
 * User: kropp
 */
public class SimpleDiceSumNotificationMaker implements DiceSumNotificationMaker {
    @Override
    public String getNotification(DiceSet diceSet) {
        if (diceSet.getDice().size() < 1)
            return null;
        int sum = diceSet.getSum();
        return sum > 0 ? "" + sum : null;
    }
}
