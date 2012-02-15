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

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.diceroller);

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        String selectedSetId = preferences.getString("selected_set_id", null);

        GamesManager gamesManager = GamesManager.getInstance();
        gamesManager.setSelected(selectedSetId);

        if (myDiceSet == null)
            myDiceSet = gamesManager.getSelectedGame().getDiceSets().get(0);
        myDiceSet.rollAll();

        final View main_area = findViewById(R.id.dice_area);
        main_area.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                RollDice();
            }
        });

        myVibrator = (Vibrator) this.getSystemService(Context.VIBRATOR_SERVICE);

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
        Game game = GamesManager.getInstance().getSelectedGame();

        // if currently selected set is not from this game, change to first set from game
        boolean found = false;
        for (DiceSet set : game.getDiceSets()) {
            if (set == myDiceSet) {
                found = true;
                break;
            }
        }
        if (!found)
            myDiceSet = game.getDiceSets().get(0);

        final TextView nameLabel = (TextView) findViewById(R.id.dicesetname);
        nameLabel.setText(game.getName());

        List<DiceSet> sets = game.getDiceSets();

        final LinearLayout setsSelector = (LinearLayout) findViewById(R.id.setsselector);
        setsSelector.removeAllViews();

        if (sets.size() > 1) {
            for (DiceSet set : sets) {
                Button label = new Button(this);
                label.setText(set.getName());
                label.setTag(set);

                label.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        myDiceSet = (DiceSet) view.getTag();
                        displaySet();
                    }
                });

                setsSelector.addView(label, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, 1));
            }
        }

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
        super.onResume();

        initView();
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

        StatsManager.getInstance(getResources()).updateStats(myDiceSet);

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

        final View view = findViewById(R.id.dicerollarea);

        int width = view.getWidth();
        if (width == 0)
            width = getWindowManager().getDefaultDisplay().getWidth();

        int height = view.getHeight();
        if (height == 0)
            height = getWindowManager().getDefaultDisplay().getHeight();

        layout.removeAllViews();

        List<Die> dice = myDiceSet.getDice();

        int size = dice.size();
        double distribution = Math.sqrt(1.0 * height / width * size);
        // trying to layout dice rectangular, otherwise adding extra items along biggest side
        int rows = (int) (width > height ? Math.floor(distribution) : Math.ceil(distribution));
        if (rows == 0)
            rows = 1;
        int columns = (int) Math.ceil(1.0 * size / rows);

        for (int i = 0; i < rows; i++) {
            TableRow row = new TableRow(this);
            row.setWeightSum(1);

            for (int j = 0; j < columns; j++) {
                int number = i * columns + j;
                if (number >= size)
                    break;

                Die die = dice.get(number);
                row.addView(die.getCurrentView(this));
            }

            layout.addView(row, new TableLayout.LayoutParams(TableLayout.LayoutParams.WRAP_CONTENT, TableLayout.LayoutParams.WRAP_CONTENT));
        }
    }
}