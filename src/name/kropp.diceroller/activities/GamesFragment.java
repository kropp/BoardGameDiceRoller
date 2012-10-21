package name.kropp.diceroller.activities;

import android.app.ListActivity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import name.kropp.diceroller.games.GamesManager;
import name.kropp.diceroller.settings.VersionManager;

/**
 * Created by IntelliJ IDEA.
 * User: kropp
 */
public class GamesFragment extends ListFragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        GamesAdapter adapter = new GamesAdapter(getActivity(), GamesManager.getInstance(getActivity()), VersionManager.getInstance());
        setListAdapter(adapter);
        //getListView().setChoiceMode(ListView.CHOICE_MODE_SINGLE);

        return super.onCreateView(inflater, container, savedInstanceState);
    }
}