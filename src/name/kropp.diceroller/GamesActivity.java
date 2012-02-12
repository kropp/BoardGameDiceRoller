package name.kropp.diceroller;

import android.app.ListActivity;
import android.os.Bundle;
import android.widget.ListView;

/**
 * Created by IntelliJ IDEA.
 * User: kropp
 */
public class GamesActivity extends ListActivity {
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        GamesAdapter adapter = new GamesAdapter(this, GamesManager.getInstance());
		setListAdapter(adapter);
        getListView().setChoiceMode(ListView.CHOICE_MODE_SINGLE);
	}
}