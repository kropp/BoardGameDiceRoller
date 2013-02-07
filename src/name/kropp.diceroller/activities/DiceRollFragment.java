package name.kropp.diceroller.activities;

import android.content.Context;
import android.content.SharedPreferences;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Vibrator;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.*;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.*;
import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.app.SherlockFragment;
import name.kropp.diceroller.R;
import name.kropp.diceroller.dice.DiceSet;
import name.kropp.diceroller.dice.Die;
import name.kropp.diceroller.dice.RethrowableDiceSet;
import name.kropp.diceroller.games.Game;
import name.kropp.diceroller.games.GamesManager;
import name.kropp.diceroller.games.StatsManager;
import name.kropp.diceroller.settings.PreferenceNames;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: kropp
 */
public class DiceRollFragment extends SherlockFragment {
    private DiceSet myDiceSet;
    private ShakeListener myShaker;
    private boolean myVibeAfterRoll;
    private boolean myShowSummaryAfterRoll;
    private Vibrator myVibrator;
    private Toast myToast;
    private Ringtone myRingtone;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.diceroller, container);

        GamesManager gamesManager = GamesManager.getInstance(getActivity());

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        String selectedGameId = preferences.getString(PreferenceNames.SelectedGameId, null);
        String selectedSetId = preferences.getString(PreferenceNames.SelectedSetId, null);

        boolean keepScreenOn = preferences.getBoolean(PreferenceNames.KeepScreenOn, false);
        if (keepScreenOn) {
            getActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        }


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

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

        final View main_area = getActivity().findViewById(R.id.dice_area);
        main_area.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if (!(myDiceSet instanceof RethrowableDiceSet))
                    RollDice();
            }
        });

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getActivity());

        myVibrator = (Vibrator) getActivity().getSystemService(Context.VIBRATOR_SERVICE);

        myVibeAfterRoll = preferences.getBoolean(PreferenceNames.Vibe, false);
        myShowSummaryAfterRoll = preferences.getBoolean(PreferenceNames.ShowSummary, true);

        String soundAfterRoll = preferences.getString(PreferenceNames.Notification, "");
        if (soundAfterRoll != null && soundAfterRoll.length() > 0) {
            myRingtone = RingtoneManager.getRingtone(getActivity(), Uri.parse(soundAfterRoll));
        } else {
            myRingtone = null;
        }

        boolean rollOnShakeEnabled = preferences.getBoolean(PreferenceNames.Shake, false);
        if (rollOnShakeEnabled) {
            listenShakeEvent();

            final TextView textView = (TextView) getActivity().findViewById(R.id.taptoroll);
            textView.setText(getString(R.string.shake_or_tap_to_roll));
        }

        preferences.registerOnSharedPreferenceChangeListener(new SharedPreferences.OnSharedPreferenceChangeListener() {
            public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String s) {
                if (s.equals(PreferenceNames.Shake)) {
                    final TextView textView = (TextView) getActivity().findViewById(R.id.taptoroll);
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
                if (s.equals(PreferenceNames.Vibe)) {
                    myVibeAfterRoll = sharedPreferences.getBoolean(s, false);
                }
                if (s.equals(PreferenceNames.ShowSummary)) {
                    myShowSummaryAfterRoll = sharedPreferences.getBoolean(s, false);
                }
                if (s.equals(PreferenceNames.Notification)) {
                    String soundAfterRoll = sharedPreferences.getString(PreferenceNames.Notification, "");
                    if (soundAfterRoll != null && soundAfterRoll.length() > 0) {
                        myRingtone = RingtoneManager.getRingtone(getActivity(), Uri.parse(soundAfterRoll));
                    } else {
                        myRingtone = null;
                    }
                }
                if (s.equals(PreferenceNames.KeepScreenOn)) {
                    boolean keepScreenOn = sharedPreferences.getBoolean(PreferenceNames.KeepScreenOn, false);
                    if (keepScreenOn) {
                        getActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
                    } else {
                        getActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
                    }
                }
            }
        });
    }

    private void initView() {
        final GamesManager gamesManager = GamesManager.getInstance(getActivity());
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

        getSherlockActivity().getSupportActionBar().setSubtitle(game.getName());

        List<DiceSet> sets = game.getDiceSets();

        final LinearLayout setsSelector = (LinearLayout) getActivity().findViewById(R.id.setsselector);
        setsSelector.removeAllViews();

        if (sets.size() > 1) {
            for (DiceSet set : sets) {
                addButton(setsSelector, set.getName(), set, true, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        myDiceSet = (DiceSet) view.getTag();

                        SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(getActivity()).edit();
                        editor.putString(PreferenceNames.SelectedSetId, myDiceSet.getId());
                        editor.commit();

                        gamesManager.setSelectedSet(myDiceSet.getId());

                        displaySet();
                    }
                });
            }
        }

        final TextView taptoroll = (TextView) getActivity().findViewById(R.id.taptoroll);

        if (myDiceSet instanceof RethrowableDiceSet) {
            final RethrowableDiceSet rethrowableDiceSet = (RethrowableDiceSet) myDiceSet;

            taptoroll.setText(R.string.tap_to_hold);

            final Button rethrowButton = addButton(setsSelector, getRethrowButtonName(rethrowableDiceSet), null, false, new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    rethrowableDiceSet.rethrow();
                    ((Button) view).setText(getRethrowButtonName(rethrowableDiceSet));
                    view.setEnabled(rethrowableDiceSet.canRethrow());
                    displaySet();
                    afterRoll();
                }
            });

            addButton(setsSelector, getString(R.string.next_turn), null, false, new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    RollDice();
                    rethrowButton.setEnabled(true);
                    rethrowButton.setText(getRethrowButtonName(rethrowableDiceSet));
                }
            });
        } else {
            taptoroll.setText(R.string.tap_to_roll);
        }

        displaySet();
    }

    private String getRethrowButtonName(RethrowableDiceSet rethrowableDiceSet) {
        return getString(R.string.rethrow) + " (" + rethrowableDiceSet.getLeftAttempts() + ")";
    }

    private Button addButton(LinearLayout setsSelector, String name, Object tag, boolean append, View.OnClickListener onClickListener) {
        Button label = new Button(getActivity());
        label.setText(name);
        label.setTag(tag);

        label.setOnClickListener(onClickListener);

        if (append)
            setsSelector.addView(label, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, 1));
        else
            setsSelector.addView(label, 0, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, 1));

        return label;
    }

    private void listenShakeEvent() {
        myShaker = new ShakeListener(getActivity());
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

        StatsManager.getInstance(getActivity()).updateStats(myDiceSet);

        afterRoll();
    }

    private void afterRoll() {
        if (myShowSummaryAfterRoll) {
            String notification = myDiceSet.getNotification();

            if (notification != null)
                if (myToast != null) {
                    myToast.setText(notification);
                    myToast.setDuration(Toast.LENGTH_LONG);
                    myToast.show();
                } else {
                    myToast = Toast.makeText(getActivity(), notification, Toast.LENGTH_LONG);
                    myToast.show();
                }
        }

        if (myVibeAfterRoll) {
            myVibrator.vibrate(100);
        }

        if (myRingtone != null) {
            if (myRingtone.isPlaying())
                myRingtone.stop();
            myRingtone.play();
        }
    }

    private void displaySet() {
        final FragmentActivity activity = getActivity();
        if (activity == null)
            return;

        final TableLayout layout = (TableLayout) activity.findViewById(R.id.dice_area);

        Animation flash = AnimationUtils.loadAnimation(activity, R.anim.flash);

        final View view = activity.findViewById(R.id.dicerollarea);

        int width = view.getMeasuredWidth();
        if (width == 0)
            width = activity.getWindowManager().getDefaultDisplay().getWidth();

        int height = view.getMeasuredHeight();
        if (height == 0)
            height = activity.getWindowManager().getDefaultDisplay().getHeight();

        layout.removeAllViews();

        List<Die> dice = myDiceSet.getDice();
        final RethrowableDiceSet rethrowableDiceSet = myDiceSet instanceof RethrowableDiceSet ? (RethrowableDiceSet) myDiceSet : null;

        int size = dice.size();
        double distribution = Math.sqrt(1.0 * height / width * size);
        // trying to layout dice rectangular, otherwise adding extra items along biggest side
        //int rows = (int) (width > height ? Math.floor(distribution) : Math.ceil(distribution));
        int rows = (int) Math.round(distribution);
        if (rows == 0)
            rows = 1;
        int columns = (int) Math.ceil(1.0 * size / rows);

        for (int i = 0; i < rows; i++) {
            final TableRow row = new TableRow(activity);
            row.setWeightSum(1);
            row.setGravity(Gravity.CENTER);

            for (int j = 0; j < columns; j++) {
                int number = i * columns + j;
                if (number >= size)
                    break;

                final int position = j;
                final Die die = dice.get(number);
                final boolean isSelected = rethrowableDiceSet != null ? rethrowableDiceSet.isSelected(die) : false;
                final View dieView = die.getCurrentView(activity, isSelected);
                if (rethrowableDiceSet != null) {
                    dieView.setOnClickListener(new DieOnClickListener(rethrowableDiceSet, die, activity, row, position));
                }
                if (!isSelected)
                    dieView.setAnimation(flash);
                row.addView(dieView);
            }

            layout.addView(row, new TableLayout.LayoutParams(TableLayout.LayoutParams.WRAP_CONTENT, TableLayout.LayoutParams.WRAP_CONTENT));
        }
    }

    private static class DieOnClickListener implements View.OnClickListener {
        private final RethrowableDiceSet rethrowableDiceSet;
        private final Die die;
        private final FragmentActivity activity;
        private final TableRow row;
        private final int position;

        public DieOnClickListener(RethrowableDiceSet rethrowableDiceSet, Die die, FragmentActivity activity, TableRow row, int position) {
            this.rethrowableDiceSet = rethrowableDiceSet;
            this.die = die;
            this.activity = activity;
            this.row = row;
            this.position = position;
        }

        @Override
        public void onClick(View view) {
            boolean sel = rethrowableDiceSet.toggle(die);
            View newView = die.getCurrentView(activity, sel);
            newView.setOnClickListener(new DieOnClickListener(rethrowableDiceSet, die, activity, row, position));
            row.removeViewAt(position);
            row.addView(newView, position);
        }
    }
}