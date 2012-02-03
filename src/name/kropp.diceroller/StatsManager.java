package name.kropp.diceroller;

import android.content.res.Resources;
import android.text.Html;
import android.text.SpannableStringBuilder;

/**
 * Created by IntelliJ IDEA.
 * User: kropp
 */
public class StatsManager {
    private static StatsManager ourInstance;

    SpannableStringBuilder myStatsText = new SpannableStringBuilder();
    private Resources myResources;

    private StatsManager(Resources resources) {
        myResources = resources;
    }

    public static StatsManager getInstance(Resources resources) {
        if (ourInstance == null) {
            ourInstance = new StatsManager(resources);
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

        myStatsText.insert(0, Html.fromHtml(builder.toString(), ResourceImageGetter.getInstance(myResources), null));
    }
}