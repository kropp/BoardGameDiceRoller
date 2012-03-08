package name.kropp.diceroller;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.*;

import java.util.Collection;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: kropp
 */
public class StatsActivity extends Activity {
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.stats);
    }

    @Override
    protected void onResume() {
        super.onResume();

        final TextView textView = (TextView) findViewById(R.id.historytext);
        textView.setText(StatsManager.getInstance(getResources()).getHistoryText());

        DiceSet set = GamesManager.getInstance(getResources()).getSelectedSet();

/*
        final TextView textView2 = (TextView) findViewById(R.id.statstext);
        textView2.setText(StatsManager.getInstance(getResources()).getStats(set));
*/
        Map<Integer, Integer> stats = StatsManager.getInstance(getResources()).getStats(set);

        final TableLayout table = (TableLayout) findViewById(R.id.statslayout);

        table.removeAllViews();

        // bars
        TableRow row1 = new TableRow(this);
        row1.setWeightSum(1);
        row1.setGravity(Gravity.BOTTOM);

        Collection<Integer> values = stats.values();
        int max = findMax(values);

        for (Integer value : values) {
            ImageView bar = new ImageView(this);
            bar.setImageDrawable(getResources().getDrawable(R.drawable.bar));
            Animation rise = AnimationUtils.loadAnimation(this, R.anim.rise);
            bar.setAnimation(rise);
            row1.addView(bar, 300 / values.size(), Math.round(80f / max * value));
            rise.start();
        }

        table.addView(row1, new TableLayout.LayoutParams(TableLayout.LayoutParams.WRAP_CONTENT, TableLayout.LayoutParams.WRAP_CONTENT));

        // values
        TableRow row2 = new TableRow(this);
        row1.setWeightSum(1);

        for (Integer value : stats.keySet()) {
            TextView label = new TextView(this);
            label.setGravity(Gravity.CENTER);
            label.setText(value.toString());
            row2.addView(label);
        }

        table.addView(row2, new TableLayout.LayoutParams(TableLayout.LayoutParams.WRAP_CONTENT, TableLayout.LayoutParams.WRAP_CONTENT));
    }

    private int findMax(Collection<Integer> values) {
        int max = 0;
        for (Integer value : values)
            if (value > max)
                max = value;
        return max;
    }
}