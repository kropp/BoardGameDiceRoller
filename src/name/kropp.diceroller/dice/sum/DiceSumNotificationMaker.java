package name.kropp.diceroller.dice.sum;

import name.kropp.diceroller.dice.DiceSet;

/**
 * Created by IntelliJ IDEA.
 * User: kropp
 */
public interface DiceSumNotificationMaker {
    String getNotification(DiceSet diceSet);
    String getHistoryString(DiceSet diceSet);
}
