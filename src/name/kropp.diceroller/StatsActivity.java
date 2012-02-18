package name.kropp.diceroller;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

/**
 * Created by IntelliJ IDEA.
 * User: kropp
 */
public class StatsActivity extends Activity {
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.stats);

/*
        final TextView textView = (TextView) findViewById(R.id.historytext);
        textView.setTextSize(20);
*/
    }

    @Override
    protected void onResume() {
        super.onResume();

        final TextView textView = (TextView) findViewById(R.id.historytext);
        textView.setText(StatsManager.getInstance(getResources()).getHistoryText());

        DiceSet set = GamesManager.getInstance(getResources()).getSelectedSet();

        final TextView textView2 = (TextView) findViewById(R.id.statstext);
        textView2.setText(StatsManager.getInstance(getResources()).getStats(set));
    }
}