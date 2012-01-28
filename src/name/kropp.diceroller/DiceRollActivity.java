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

    public DiceRollActivity() {
        myDiceSet = new DiceSet();

        long seed = System.currentTimeMillis();
        myDiceSet.addDice(new Dice6(seed));
        myDiceSet.addDice(new RedDice6(seed * System.currentTimeMillis()));
        myDiceSet.addDice(new SettlersOfCatanCitiesAndKnightsEventDice(seed * seed * System.currentTimeMillis()));
    }

    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.diceroller);

        final TextView setName = (TextView) findViewById(R.id.dicesetname);
        setName.setText(myDiceSet.getName());
        
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
                if (s.equals("shake_preference"))
                {
                    final TextView textView = (TextView) findViewById(R.id.taptoroll);
                    boolean value = sharedPreferences.getBoolean(s, false);
                    if (value) {
                        if (myShaker == null)
                            listenShakeEvent();
                        else
                            myShaker.resume();

                        textView.setText(getString(R.string.shake_or_tap_to_roll));
                    }
                    else
                    {
                        if (myShaker != null)
                            myShaker.pause();

                        textView.setText(getString(R.string.tap_to_roll));
                    }
                }
                if (s.equals("vibe_preference"))
                {
                    myVibeAfterRoll = sharedPreferences.getBoolean("vibe_preference", false);
                }
            }
        });
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

        List<Dice> dices = myDiceSet.getDices();
        
        updateDiceImage(R.id.white_dice, dices.get(0).getIconId());
        updateDiceImage(R.id.red_dice, dices.get(1).getIconId());
        updateDiceImage(R.id.event_dice, dices.get(2).getIconId());

        StatsManager.getInstance().updateStats(myDiceSet);

        if (myVibeAfterRoll)
        {
            myVibrator.vibrate(150);
        }
    }

    private void updateDiceImage(int dice, int icon) {
        final ImageView imageView = (ImageView) findViewById(dice);
        imageView.setImageResource(icon);
    }
}