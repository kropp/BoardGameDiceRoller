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

        final TextView textView = (TextView) findViewById(R.id.statstext);
        textView.setTextSize(20);
    }

    @Override
    protected void onResume() {
        super.onResume();

        final TextView textView = (TextView) findViewById(R.id.statstext);
        textView.setText(StatsManager.getInstance(getResources()).getText());
    }
}