package name.kropp.diceroller;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Vibrator;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.*;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: kropp
 */
public class DiceRollActivity extends Activity {
    private DiceSet myDiceSet;
    private ShakeListener myShaker;
    private boolean myVibeAfterRoll;
    private Vibrator myVibrator;
    private Toast myToast;

    public DiceRollActivity() {
        myDiceSet = SetsManager.getInstance().getSets().get(0);
        myDiceSet.rollAll();
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.diceroller);

        initView();

        final View main_area = findViewById(R.id.dice_area);
        main_area.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                RollDice();
            }
        });

        myVibrator = (Vibrator) this.getSystemService(Context.VIBRATOR_SERVICE);

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        myVibeAfterRoll = preferences.getBoolean("vibe_preference", false);
        boolean rollOnShakeEnabled = preferences.getBoolean("shake_preference", false);
        if (rollOnShakeEnabled) {
            listenShakeEvent();

            final TextView textView = (TextView) findViewById(R.id.taptoroll);
            textView.setText(getString(R.string.shake_or_tap_to_roll));
        }

        preferences.registerOnSharedPreferenceChangeListener(new SharedPreferences.OnSharedPreferenceChangeListener() {
            public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String s) {
                if (s.equals("shake_preference")) {
                    final TextView textView = (TextView) findViewById(R.id.taptoroll);
                    boolean value = sharedPreferences.getBoolean(s, false);
                    if (value) {
                        if (myShaker == null)
                            listenShakeEvent();
                        else
                            myShaker.resume();

                        textView.setText(getString(R.string.shake_or_tap_to_roll));
                    } else {
                        if (myShaker != null)
                            myShaker.pause();

                        textView.setText(getString(R.string.tap_to_roll));
                    }
                }
                if (s.equals("vibe_preference")) {
                    myVibeAfterRoll = sharedPreferences.getBoolean("vibe_preference", false);
                }
            }
        });
    }

    private void initView() {
        myDiceSet = SetsManager.getInstance().getSelectedSet();

        final TextView setName = (TextView) findViewById(R.id.dicesetname);
        setName.setText(myDiceSet.getName());
        displaySet();
    }

    private void listenShakeEvent() {
        myShaker = new ShakeListener(this);
        myShaker.setOnShakeListener(new ShakeListener.OnShakeListener() {
            public void onShake() {
                RollDice();
            }
        });
    }

    @Override
    public void onResume() {
        if (myShaker != null) {
            myShaker.resume();
        }

        initView();

        super.onResume();
    }

    @Override
    public void onPause() {
        if (myShaker != null) {
            myShaker.pause();
        }
        super.onPause();
    }

    private void RollDice() {
        myDiceSet.rollAll();

        displaySet();

        StatsManager.getInstance().updateStats(myDiceSet);

        String notification = "" + myDiceSet.getSum();
        if (myToast != null) {
            myToast.setText(notification);
            myToast.setDuration(Toast.LENGTH_LONG);
            myToast.show();
        } else {
            myToast = Toast.makeText(this, notification, Toast.LENGTH_LONG);
            myToast.show();
        }

        if (myVibeAfterRoll) {
            myVibrator.vibrate(150);
        }
    }

    private void displaySet() {
        final TableLayout layout = (TableLayout) findViewById(R.id.dice_area);
        layout.removeAllViews();

        List<Die> dies = myDiceSet.getDice();

        int size = (int) Math.ceil(Math.sqrt(dies.size()));
        for (int i = 0; i < size; i++) {
            TableRow row = new TableRow(this);

            for (int j = 0; j < size && i * size + j < dies.size(); j++) {
                Die die = dies.get(i * size + j);

                ImageView image = new ImageView(this);
                image.setImageResource(die.getIconId());
                image.setPadding(5, 5, 5, 5);

                row.addView(image);
            }

            layout.addView(row, new TableLayout.LayoutParams(TableLayout.LayoutParams.WRAP_CONTENT, TableLayout.LayoutParams.WRAP_CONTENT));
        }
    }
}