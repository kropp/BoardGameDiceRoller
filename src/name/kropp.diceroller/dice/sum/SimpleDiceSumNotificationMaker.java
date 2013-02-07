package name.kropp.diceroller.dice.sum;

import name.kropp.diceroller.dice.DiceSet;
import name.kropp.diceroller.dice.Die;

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
            builder.append("= <b>").append(diceSet.getSum()).append("</b>");
        builder.append("<br/>");

        return builder.toString();
    }
}
