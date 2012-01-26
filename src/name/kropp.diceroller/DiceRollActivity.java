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

/**
 * Created by IntelliJ IDEA.
 * User: kropp
 */
public class DiceRollActivity extends Activity {
    private Dice6 myWhiteDice;
    private RedDice6 myRedDice;
    private SettlersOfCatanCitiesAndKnightsEventDice myEventDice;
    private ShakeListener myShaker;
    private boolean myVibeAfterRoll;
    private Vibrator myVibrator;

    public DiceRollActivity() {
        long seed = System.currentTimeMillis();
        myWhiteDice = new Dice6(seed);
        myRedDice = new RedDice6(seed * System.currentTimeMillis());
        myEventDice = new SettlersOfCatanCitiesAndKnightsEventDice(seed * seed * System.currentTimeMillis());
    }

    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.diceroller);

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
        }

        preferences.registerOnSharedPreferenceChangeListener(new SharedPreferences.OnSharedPreferenceChangeListener() {
            public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String s) {
                if (s.equals("shake_preference"))
                {
                    boolean value = sharedPreferences.getBoolean(s, false);
                    if (value) {
                        if (myShaker == null)
                            listenShakeEvent();
                        else
                            myShaker.resume();
                    }
                    else
                    {
                        if (myShaker != null)
                            myShaker.pause();
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
        myWhiteDice.roll();
        myRedDice.roll();
        myEventDice.roll();

        updateDiceImage(R.id.white_dice, myWhiteDice.getIconId());
        updateDiceImage(R.id.red_dice, myRedDice.getIconId());
        updateDiceImage(R.id.event_dice, myEventDice.getIconId());

        StatsManager.getInstance().updateStats(myWhiteDice.getCurrentValue(), myRedDice.getCurrentValue(), myEventDice.getCurrentEvent());

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