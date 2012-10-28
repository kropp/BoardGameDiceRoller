package name.kropp.diceroller.smallapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Vibrator;
import android.preference.PreferenceManager;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.*;
import com.sony.smallapp.SmallAppWindow;
import com.sony.smallapp.SmallApplication;
import name.kropp.diceroller.R;
import name.kropp.diceroller.activities.MyMainActivity;
import name.kropp.diceroller.dice.DiceSet;
import name.kropp.diceroller.dice.Die;
import name.kropp.diceroller.games.Game;
import name.kropp.diceroller.games.GamesManager;
import name.kropp.diceroller.games.StatsManager;
import name.kropp.diceroller.settings.PreferenceNames;

import java.util.List;

public class BoardGameDiceRollerSmallApp extends SmallApplication {
    private DiceSet myDiceSet;
    private Toast myToast;

    @Override
    public void onCreate() {
        super.onCreate();
        setContentView(R.layout.smallapp);
        setTitle(R.string.app_name);

        SmallAppWindow.Attributes attr = getWindow().getAttributes();
        attr.minWidth = 300;
        attr.minHeight = 200;
        attr.width = 400;
        attr.height = 300;
        attr.flags |= SmallAppWindow.Attributes.FLAG_RESIZABLE;
        attr.flags |= SmallAppWindow.Attributes.FLAG_NO_TITLEBAR;
        getWindow().setAttributes(attr);
    }

    @Override
    public void onStart() {
        super.onStart();

        GamesManager gamesManager = GamesManager.getInstance(this);

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        String selectedGameId = preferences.getString(PreferenceNames.SelectedGameId, null);
        String selectedSetId = preferences.getString(PreferenceNames.SelectedSetId, null);

        gamesManager.setSelectedGame(selectedGameId);

        if (myDiceSet == null) {
            List<DiceSet> diceSets = gamesManager.getSelectedGame().getDiceSets();
            for (DiceSet diceSet : diceSets) {
                if (diceSet.getId().equals(selectedSetId)) {
                    myDiceSet = diceSet;
                    break;
                }
            }
            if (myDiceSet == null)
                myDiceSet = diceSets.get(0);
        }
        gamesManager.setSelectedSet(myDiceSet.getId());
        final View main_area = findViewById(R.id.dice_area);
        main_area.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                RollDice();
            }
        });

        preferences.registerOnSharedPreferenceChangeListener(new SharedPreferences.OnSharedPreferenceChangeListener() {
            @Override
            public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String s) {
                if (s.equals(PreferenceNames.SelectedGameId) || s.equals(PreferenceNames.SelectedSetId)) {
                    displaySet();
                }
            }
        });

        initView();
    }

    private void initView() {
        final GamesManager gamesManager = GamesManager.getInstance(this);
        Game game = gamesManager.getSelectedGame();

        // if currently selected set is not from this game, change to first set from game
        boolean found = false;
        for (DiceSet set : game.getDiceSets()) {
            if (set == myDiceSet) {
                found = true;
                break;
            }
        }
        if (!found) {
            myDiceSet = game.getDiceSets().get(0);
            gamesManager.setSelectedSet(myDiceSet.getId());
        }

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

                final BoardGameDiceRollerSmallApp context = this;
                label.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        myDiceSet = (DiceSet) view.getTag();

                        SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(context).edit();
                        editor.putString(PreferenceNames.SelectedSetId, myDiceSet.getId());
                        editor.commit();

                        gamesManager.setSelectedSet(myDiceSet.getId());

                        displaySet();
                    }
                });

                setsSelector.addView(label, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, 1));
            }
        }

        ImageButton appbutton = (ImageButton) findViewById(R.id.appbutton);
        final Context context = this;
        appbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, MyMainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });

        displaySet();
    }

    private void RollDice() {
        myDiceSet.rollAll();

        displaySet();

        StatsManager.getInstance(this).updateStats(myDiceSet);
    }

    private void displaySet() {
        final TableLayout layout = (TableLayout) findViewById(R.id.dice_area);

        Animation flash = AnimationUtils.loadAnimation(this, R.anim.flash);
        layout.setAnimation(flash);

        final View view = findViewById(R.id.dicerollarea);

        Display display = ((WindowManager) getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();

        int width = view.getMeasuredWidth();
        if (width == 0)
            width = display.getWidth();

        int height = view.getMeasuredHeight();
        if (height == 0)
            height = display.getHeight();

        layout.removeAllViews();

        List<Die> dice = myDiceSet.getDice();

        int size = dice.size();
        double distribution = Math.sqrt(1.0 * height / width * size);
        // trying to layout dice rectangular, otherwise adding extra items along biggest side
        //int rows = (int) (width > height ? Math.floor(distribution) : Math.ceil(distribution));
        int rows = (int) Math.round(distribution);
        if (rows == 0)
            rows = 1;
        int columns = (int) Math.ceil(1.0 * size / rows);

        for (int i = 0; i < rows; i++) {
            TableRow row = new TableRow(this);
            row.setWeightSum(1);
            row.setGravity(Gravity.CENTER);

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

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}