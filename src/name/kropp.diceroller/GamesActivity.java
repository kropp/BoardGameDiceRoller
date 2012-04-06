package name.kropp.diceroller;

import android.app.Dialog;
import android.app.ListActivity;
import android.os.Bundle;
import android.widget.ListView;

/**
 * Created by IntelliJ IDEA.
 * User: kropp
 */
public class GamesActivity extends ListActivity {
    public static final int FULL_VERSION_DIALOG_ID = 1;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        GamesAdapter adapter = new GamesAdapter(this, GamesManager.getInstance(getResources()), VersionManager.getInstance());
		setListAdapter(adapter);
        getListView().setChoiceMode(ListView.CHOICE_MODE_SINGLE);
	}

    @Override
    protected Dialog onCreateDialog(int id) {
        if (id == FULL_VERSION_DIALOG_ID) {
            return createAboutDialog();
        }
        return null;
    }

    private Dialog createAboutDialog() {
        Dialog dialog = new Dialog(this);

        dialog.setContentView(R.layout.about);
        dialog.setTitle(R.string.about_title);
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(true);

        return dialog;
    }
}