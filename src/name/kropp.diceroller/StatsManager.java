package name.kropp.diceroller;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: kropp
 */
public class StatsManager {
    private static StatsManager ourInstance;

    final static int MAX_LINES = 20;
    
    private String[] myLines = new String[MAX_LINES];

    private StatsManager() {
        for (int i = 0; i < MAX_LINES; i++)
            myLines[i] = "\n";
    }

    public static StatsManager getInstance()
    {
        if (ourInstance == null)
        {
            // not thread-safe, consider rewriting
            ourInstance = new StatsManager();
        }
        return ourInstance;
    }

    public CharSequence getText()
    {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < MAX_LINES; i++) {
            builder.append(myLines[i]).append('\n');
        }
        return builder.toString();
    }

    public void updateStats(DiceSet diceSet) {
        for (int i = MAX_LINES - 1; i > 0; i--)
            myLines[i] = myLines[i-1];

        List<Dice> dices = diceSet.getDices();
        int whiteDice = dices.get(0).getCurrentValue();
        int redDice = dices.get(1).getCurrentValue();
        Event eventDice = ((SettlersOfCatanCitiesAndKnightsEventDice)dices.get(2)).getCurrentEvent();

        myLines[0] = String.format("%d [%d] = %d %s", whiteDice, redDice, whiteDice + redDice, eventDice);
    }
}
