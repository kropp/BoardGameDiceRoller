package name.kropp.diceroller;

import android.text.Html;
import android.text.SpannableStringBuilder;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: kropp
 */
public class StatsManager {
    private static StatsManager ourInstance;
    SpannableStringBuilder myStatsText = new SpannableStringBuilder();

    private StatsManager() {
    }

    public static StatsManager getInstance() {
        if (ourInstance == null) {
            ourInstance = new StatsManager();
        }
        return ourInstance;
    }

    public CharSequence getText() {
        return myStatsText;
    }

    public void updateStats(DiceSet diceSet) {
        StringBuilder builder = new StringBuilder();

        for (Die die : diceSet.getDice()) {
            builder.append(die).append(' ');
        }

        builder.append("= <b>").append(diceSet.getSum()).append("</b><br>");

        myStatsText.insert(0, Html.fromHtml(builder.toString()));
    }
}