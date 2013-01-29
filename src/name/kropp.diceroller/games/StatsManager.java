package name.kropp.diceroller.games;

import android.content.Context;
import android.text.Html;
import android.text.SpannableStringBuilder;
import name.kropp.diceroller.activities.ResourceImageGetter;
import name.kropp.diceroller.dice.DiceSet;
import name.kropp.diceroller.dice.Die;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: kropp
 */
public class StatsManager {
    private static StatsManager ourInstance;

    private SpannableStringBuilder myHistoryText = new SpannableStringBuilder();
    private ResourceImageGetter myResourceImageGetter;
    private Map<Die, List<Integer>> myHistoryValues = new HashMap<Die, List<Integer>>();

    private StatsManager(Context context) {
        myResourceImageGetter = ResourceImageGetter.getInstance(context);
    }

    public static StatsManager getInstance(Context context) {
        if (ourInstance == null) {
            ourInstance = new StatsManager(context);
        }
        return ourInstance;
    }

    public Map<Integer, Integer> getStats(DiceSet diceSet) {
        if (diceSet == null)
            return null;

        // prepare map from value to count
        Map<Integer, Integer> values = new HashMap<Integer, Integer>();
        for (Die die : diceSet.getDice()) {
            if (myHistoryValues.containsKey(die)) {
                for (Integer i : myHistoryValues.get(die)) {
                    values.put(i, values.containsKey(i) ? values.get(i) + 1 : 1);
                }
            }
        }

        return values;
    }
    
    public CharSequence getHistoryText() {
        return myHistoryText;
    }

    public void updateStats(DiceSet diceSet) {
        StringBuilder builder = new StringBuilder();

        boolean first = true;
        for (Die die : diceSet.getDice()) {
            if (!first)
                builder.append("+ ");
            builder.append(die).append(' ');
            addHistoryValue(die);
            first = false;
        }

        if (diceSet.getDice().size() > 1 && diceSet.getSum() > 0)
            builder.append("= <b>").append(diceSet.getSum()).append("</b>");
        builder.append("<br>");

        myHistoryText.insert(0, Html.fromHtml(builder.toString(), myResourceImageGetter, null));
    }

    private void addHistoryValue(Die die) {
        List<Integer> historyValues = myHistoryValues.get(die);
        if (historyValues == null) {
            historyValues = new ArrayList<Integer>();
            myHistoryValues.put(die, historyValues);
        }
        historyValues.add(die.getCurrentValue());
    }

    public void clear() {
        myHistoryValues.clear();
        myHistoryText.clear();
    }
}