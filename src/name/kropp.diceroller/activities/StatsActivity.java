package name.kropp.diceroller.activities;

import android.app.Activity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.*;
import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.MenuItem;
import name.kropp.diceroller.R;
import name.kropp.diceroller.dice.DiceSet;
import name.kropp.diceroller.games.GamesManager;
import name.kropp.diceroller.games.StatsManager;
import name.kropp.diceroller.settings.Version;
import name.kropp.diceroller.settings.VersionManager;

import java.util.Collection;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: kropp
 */
public class StatsActivity extends SherlockActivity {
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(R.string.history_header);

        setContentView(R.layout.stats);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();

        final TextView textView = (TextView) findViewById(R.id.historytext);
        textView.setText(StatsManager.getInstance(this).getHistoryText());

        GamesManager gamesManager = GamesManager.getInstance(this);
        DiceSet set = gamesManager.getSelectedSet();

        getSupportActionBar().setSubtitle(gamesManager.getSelectedGame().getName());

        Map<Integer, Integer> stats = StatsManager.getInstance(this).getStats(set);

/*
        final TableLayout table = (TableLayout) findViewById(R.id.statslayout);

        if (VersionManager.getInstance().getVersion() == Version.FULL) {
            table.removeAllViews();
            //table.setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, Gravity.BOTTOM));

            Collection<Integer> values = stats.values();
            if (values.size() > 0) {
                // bars
                TableRow row1 = new TableRow(this);
                //row1.setWeightSum(0.1f);
                row1.setGravity(Gravity.BOTTOM);

                int width = 200;
                int height = table.getMeasuredHeight() - 40;
                if (height < 10)
                    height = 100;

                int max = findMax(values);

                for (Integer value : values) {
                    ImageView bar = new ImageView(this);
                    bar.setImageDrawable(getResources().getDrawable(R.drawable.bar));
                    Animation rise = AnimationUtils.loadAnimation(this, R.anim.rise);
                    bar.setAnimation(rise);
                    bar.setAdjustViewBounds(true);
                    row1.addView(bar, width / values.size(), Math.round(1f * height / max * value));
                    rise.start();
                }

                table.addView(row1, new TableLayout.LayoutParams(TableLayout.LayoutParams.FILL_PARENT, TableLayout.LayoutParams.FILL_PARENT));

                // values
                TableRow row2 = new TableRow(this);
                //row2.setWeightSum(1.9f);

                for (Integer value : stats.keySet()) {
                    TextView label = new TextView(this);
                    label.setGravity(Gravity.CENTER);
                    label.setText(value.toString());
                    row2.addView(label);
                }

                table.addView(row2, new TableLayout.LayoutParams(TableLayout.LayoutParams.FILL_PARENT, TableLayout.LayoutParams.FILL_PARENT));
            }
        } else {
            final Activity context = this;
            Button buyButton = (Button) findViewById(R.id.buy_button);
            if (buyButton != null) {
                buyButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        VersionManager.getInstance().showBuyDialog(context);
                    }
                });
            }
        }
*/
    }

    private int findMax(Collection<Integer> values) {
        int max = 0;
        for (Integer value : values)
            if (value > max)
                max = value;
        return max;
    }
}