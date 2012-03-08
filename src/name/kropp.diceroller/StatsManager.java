package name.kropp.diceroller;

import android.content.res.Resources;
import android.text.Html;
import android.text.SpannableStringBuilder;

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

    private StatsManager(Resources resources) {
        myResourceImageGetter = ResourceImageGetter.getInstance(resources);
    }

    public static StatsManager getInstance(Resources resources) {
        if (ourInstance == null) {
            ourInstance = new StatsManager(resources);
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

        builder.append("= <b>").append(diceSet.getSum()).append("</b><br>");

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
}