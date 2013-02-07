package name.kropp.diceroller.dice.sum;

import name.kropp.diceroller.dice.DiceSet;
import name.kropp.diceroller.dice.Die;

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

    @Override
    public String getHistoryString(DiceSet diceSet) {
        StringBuilder builder = new StringBuilder();

        boolean first = true;
        for (Die die : diceSet.getDice()) {
            if (!first)
                builder.append("+ ");
            builder.append(die).append(' ');
            first = false;
        }

        if (diceSet.getDice().size() > 1 && diceSet.getSum() > 0)
            builder.append("+ <b>2</b> = <b>").append(diceSet.getSum()).append("</b>");
        builder.append("<br/>");

        return builder.toString();
    }
}
