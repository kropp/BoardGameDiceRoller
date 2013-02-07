package name.kropp.diceroller.dice.sum;

import name.kropp.diceroller.dice.DiceSet;
import name.kropp.diceroller.dice.Die;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: kropp
 */
public class EvolutionContinentsDiceSumNotificationMaker implements DiceSumNotificationMaker {
    private int[] myNumGondwanasDice = { 1, 2, 2, 2, 2, 3, 3 };
    private int[] myNumLaurasiasDice = { 1, 1, 1, 1, 1, 2, 2 };

    private int[] myGondwanasBonus = { 2, 0, 0, 2, 3, 2, 4 };
    private int[] myLaurasiasBonus = { 0, 0, 2, 3, 5, 2, 2 };

    @Override
    public String getNotification(DiceSet diceSet) {
        int index = Integer.parseInt(diceSet.getName()) - 2;

        int numDice = myNumGondwanasDice[index];
        int sumGondwana = getSum(diceSet.getDice(), 0, numDice);
        int sumLaurasia = getSum(diceSet.getDice(), numDice, numDice + myNumLaurasiasDice[index]);

        String resultGondwana = getResultString(sumGondwana, myGondwanasBonus[index]);
        String resultLaurasia = getResultString(sumLaurasia, myLaurasiasBonus[index]);

        return String.format("Gondwana: %s\nLaurasia: %s\nOcean: %d", resultGondwana, resultLaurasia, index + 3);
    }

    @Override
    public String getHistoryString(DiceSet diceSet) {
        int index = Integer.parseInt(diceSet.getName()) - 2;

        int numDice = myNumGondwanasDice[index];
        int sumGondwana = getSum(diceSet.getDice(), 0, numDice);
        int sumLaurasia = getSum(diceSet.getDice(), numDice, numDice + myNumLaurasiasDice[index]);

        String resultGondwana = getResultHtmlString(sumGondwana, getSumString(diceSet.getDice(), 0, numDice), myGondwanasBonus[index]);
        String resultLaurasia = getResultHtmlString(sumLaurasia, getSumString(diceSet.getDice(), numDice, numDice + myNumLaurasiasDice[index]), myLaurasiasBonus[index]);

        return String.format("Gondwana: %s<br/>Laurasia: %s<br/>Ocean: %d<br/><br/>", resultGondwana, resultLaurasia, index + 3);
    }

    private String getSumString(List<Die> dice, int from, int to) {
        StringBuilder builder = new StringBuilder();

        boolean first = true;
        for (int i = from; i < to; i++) {
            if (!first)
                builder.append("+ ");
            builder.append(dice.get(i)).append(' ');
            first = false;
        }

        return builder.toString();
    }

    private String getResultString(int sum, int bonus) {
        return bonus > 0 ? sum + " + " + bonus + " = " + (sum + bonus) : "" + sum;
    }

    private String getResultHtmlString(int sum, String sumString, int bonus) {
        return bonus > 0 ? sumString + " + <b>" + bonus + "</b> = <b>" + (sum + bonus) + "</b>": "<b>" + sum + "</b>";
    }

    private int getSum(List<Die> dice, int from, int to) {
        int result = 0;

        for (int i = from; i < to; i++) {
            result += dice.get(i).getCurrentValue();
        }

        return result;
    }
}
