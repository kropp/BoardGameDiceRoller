package name.kropp.diceroller;

import android.R;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

/**
 * Created by IntelliJ IDEA.
 * User: kropp
 */
public class GamesAdapter extends ArrayAdapter<String> {
    private GamesManager myGamesManager;
    private Context myContext;
    private SharedPreferences myPreferences;

    public GamesAdapter(Context context, GamesManager gamesManager) {
        super(context, R.layout.simple_list_item_single_choice);

        myContext = context;
        myPreferences = PreferenceManager.getDefaultSharedPreferences(context);
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
        LayoutInflater inflater = (LayoutInflater) myContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        TextView textView = (TextView) inflater.inflate(R.layout.simple_list_item_1, parent, false);

        textView.setText(myGamesManager.getGames().get(position).getName());
        textView.setTag(position);

        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myGamesManager.setSelectedGame((Integer) view.getTag());
                SharedPreferences.Editor editor = myPreferences.edit();
                editor.putString(myContext.getString(name.kropp.diceroller.R.string.selected_game_id_preference_name), myGamesManager.getSelectedGame().getId());
                editor.commit();
                myContext.startActivity(new Intent(myContext, MyMainActivity.class));
            }
        });

        return textView;
    }
}