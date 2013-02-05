package name.kropp.diceroller.dice.sum;

import name.kropp.diceroller.dice.DiceSet;

/**
 * Created by IntelliJ IDEA.
 * User: kropp
 */
public class PlusTwoDiceSumNotificationMaker implements DiceSumNotificationMaker {
    @Override
    public String getNotification(DiceSet diceSet) {
        int sum = diceSet.getSum();
        return sum + " + 2 = " + (sum+2);
    }
}
