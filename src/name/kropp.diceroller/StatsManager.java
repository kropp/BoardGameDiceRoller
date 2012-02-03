package name.kropp.diceroller;

import android.graphics.Typeface;
import android.text.Html;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.StyleSpan;

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

        List<Die> dice = diceSet.getDice();
        for (Die die : dice) {
            if (die instanceof SettlersOfCatanCitiesAndKnightsEventDie) {
                builder.append(((SettlersOfCatanCitiesAndKnightsEventDie) die).getCurrentEvent().toString()).append(' ');
            } else {
                if (die instanceof RedDie6) {
                    builder.append("<font color=\"red\">").append(die.getCurrentValue()).append("</font> ");
                } else {
                    builder.append(die.getCurrentValue()).append(' ');
                }
            }
        }

        builder.append("= <b>").append(diceSet.getSum()).append("</b><br>");

        myStatsText.insert(0, Html.fromHtml(builder.toString()));
    }
}
