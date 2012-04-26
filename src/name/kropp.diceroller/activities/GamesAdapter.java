package name.kropp.diceroller.activities;

import android.R;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.TwoLineListItem;
import name.kropp.diceroller.games.Game;
import name.kropp.diceroller.games.GamesManager;
import name.kropp.diceroller.Version;
import name.kropp.diceroller.VersionManager;

/**
 * Created by IntelliJ IDEA.
 * User: kropp
 */
public class GamesAdapter extends ArrayAdapter<String> {
    private GamesManager myGamesManager;
    private Activity myActivity;
    private SharedPreferences myPreferences;
    private VersionManager myVersionManager;

    public GamesAdapter(Activity activity, GamesManager gamesManager, VersionManager versionManager) {
        super(activity, R.layout.simple_list_item_2);

        myActivity = activity;
        myVersionManager = versionManager;
        myPreferences = PreferenceManager.getDefaultSharedPreferences(activity);
        myGamesManager = gamesManager;
    }

    @Override
    public int getCount() {
        return myGamesManager.getGames().size();
    }

    @Override
    public String getItem(int position) {
        return myGamesManager.getGames().get(position).getName();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) myActivity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        Game game = myGamesManager.getGames().get(position);

        if (myVersionManager.getVersion() == Version.LITE && game.getVersion() == Version.FULL) {
            TwoLineListItem listItem = (TwoLineListItem) inflater.inflate(R.layout.simple_list_item_2, parent, false);
            listItem.getText1().setText(game.getName());
            listItem.getText2().setText(myActivity.getString(name.kropp.diceroller.R.string.only_in_full_version));
            listItem.setTag(position);

            listItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    myVersionManager.showBuyDialog(myActivity);
                }
            });

            return listItem;
        } else {
            TextView textView = (TextView) inflater.inflate(R.layout.simple_list_item_1, parent, false);
            textView.setText(game.getName());

            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    myGamesManager.setSelectedGame((Integer) view.getTag());
                    SharedPreferences.Editor editor = myPreferences.edit();
                    editor.putString(myActivity.getString(name.kropp.diceroller.R.string.selected_game_id_preference_name), myGamesManager.getSelectedGame().getId());
                    editor.commit();

                    Intent intent = new Intent(myActivity, MyMainActivity.class);
                    intent.putExtra("tab", "rolldice");
                    myActivity.startActivity(intent);
                }
            });

            textView.setTag(position);
            return textView;
        }
    }
}