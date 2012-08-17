package name.kropp.diceroller.activities;

import android.app.ListActivity;
import android.os.Bundle;
import android.widget.ListView;
import name.kropp.diceroller.games.GamesManager;
import name.kropp.diceroller.settings.VersionManager;

/**
 * Created by IntelliJ IDEA.
 * User: kropp
 */
public class GamesActivity extends ListActivity {
    public static final int FULL_VERSION_DIALOG_ID = 1;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        GamesAdapter adapter = new GamesAdapter(this, GamesManager.getInstance(this), VersionManager.getInstance());
		setListAdapter(adapter);
        getListView().setChoiceMode(ListView.CHOICE_MODE_SINGLE);
	}
}