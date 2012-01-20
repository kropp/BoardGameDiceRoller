package name.kropp.diceroller;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Vibrator;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class MyMainActivity extends Activity
{
    private Dice6 myWhiteDice;
    private RedDice6 myRedDice;
    private SettlersOfCatanCitiesAndKnightsEventDice myEventDice;
    private ShakeListener myShaker;
    private boolean myVibeAfterRoll;
    private Vibrator myVibrator;

    static final int ABOUT_DIALOG_ID = 1;
    
    public MyMainActivity() {
        long seed = System.currentTimeMillis();
        myWhiteDice = new Dice6(seed);
        myRedDice = new RedDice6(seed * System.currentTimeMillis());
        myEventDice = new SettlersOfCatanCitiesAndKnightsEventDice(seed * seed * System.currentTimeMillis());
    }

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        final Button button = (Button) findViewById(R.id.button_id);
        button.setOnClickListener(new View.OnClickListener() {
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

        final TextView textView = (TextView) findViewById(R.id.text);
        textView.setTextSize(20);
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
        case R.id.options:
            openOptions();
            return true;
        case R.id.about:
            showDialog(ABOUT_DIALOG_ID);
            return true;
        default:
            return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        if (id == ABOUT_DIALOG_ID)
        {
            return createAboutDialog();
        }
        return null;
    }

    private Dialog createAboutDialog() {
        Dialog dialog = new Dialog(this);

        dialog.setContentView(R.layout.about);
        dialog.setTitle("About Board Game Dice Roller");
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(true);
/*
        ImageView image = (ImageView) dialog.findViewById(R.id.image);
        image.setImageResource(R.drawable.android);
*/
        return dialog;
    }

    private void openOptions() {
        Intent myIntent = new Intent(this, MyPreferencesActivity.class);
        this.startActivity(myIntent);
    }

    private void RollDice() {
        myWhiteDice.roll();
        myRedDice.roll();
        myEventDice.roll();

        updateDiceImage(R.id.white_dice, myWhiteDice.getIconId());
        updateDiceImage(R.id.red_dice, myRedDice.getIconId());
        updateDiceImage(R.id.event_dice, myEventDice.getIconId());

        final TextView textView = (TextView) findViewById(R.id.text);

        String[] lines = textView.getText().toString().split("\n", -1);
        StringBuilder old = new StringBuilder();
        for (int i = 0; i < 5; i++)
            old.append(lines[i]).append("\n");

        CharSequence newText = String.format("%d [%d] = %d %s\n%s", myWhiteDice.getCurrentValue(), myRedDice.getCurrentValue(), myWhiteDice.getCurrentValue() + myRedDice.getCurrentValue(), myEventDice.getCurrentEvent().toString(), old.toString());
        textView.setText(newText);
        
        if (myVibeAfterRoll)
        {
            myVibrator.vibrate(500);
        }
    }

    private void updateDiceImage(int dice, int icon) {
        final ImageView imageView = (ImageView) findViewById(dice);
        imageView.setImageResource(icon);
    }
}
