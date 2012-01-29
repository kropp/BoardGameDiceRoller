package name.kropp.diceroller;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Vibrator;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

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

        final LinearLayout main_area = (LinearLayout) findViewById(R.id.dice_area);
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
        final LinearLayout layout = (LinearLayout) findViewById(R.id.dice_area);
        layout.removeAllViews();
        for (Dice dice : myDiceSet.getDices()) {
            ImageView image = new ImageView(this);
            LinearLayout.LayoutParams vp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.FILL_PARENT, 1);
            image.setLayoutParams(vp);
            layout.addView(image);
            image.setImageResource(dice.getIconId());
        }
    }
}