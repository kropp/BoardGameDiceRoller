package name.kropp.diceroller.activities;

import android.app.Dialog;
import android.app.TabActivity;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TabHost;
import name.kropp.diceroller.R;
import name.kropp.diceroller.games.GamesManager;
import name.kropp.diceroller.settings.VersionManager;

public class MyMainActivity extends FragmentActivity {
    static final int ABOUT_DIALOG_ID = 1;
    static final int GAMES_DIALOG_ID = 2;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE)
        {
            menu.removeItem(R.id.choosegame);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.choosegame:
                showDialog(GAMES_DIALOG_ID);
                return true;
            case R.id.options:
                openOptions();
                return true;
            case R.id.about:
                showDialog(ABOUT_DIALOG_ID);
                return true;
/*
            case R.id.clearhistory:
                StatsManager.getInstance(this).clear();

                if (getTabHost().getCurrentTab() == 1) { // reload stats view
                    Intent intent = new Intent(this, MyMainActivity.class);
                    intent.putExtra("tab", "stats");
                    startActivity(intent);
                }
                return true;
*/
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private Dialog createGamesDialog() {
        Dialog dialog = new Dialog(this);

        ListView gamesView = new ListView(this);
        GamesAdapter adapter = new GamesAdapter(this, GamesManager.getInstance(this), VersionManager.getInstance());
        gamesView.setAdapter(adapter);

        dialog.setContentView(gamesView);
        dialog.setTitle(R.string.games_tab_name);
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(true);

        return dialog;
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        if (id == ABOUT_DIALOG_ID) {
            return createAboutDialog();
        } else if (id == GAMES_DIALOG_ID) {
            return createGamesDialog();
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

    private void openOptions() {
        this.startActivity(new Intent(this, MyPreferencesActivity.class));
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        int index = 0;
        if (intent.getExtras() != null) {
            String tabName = intent.getExtras().getString("tab");
            if (tabName != null && tabName.equals("stats"))
                index = 1;
        }
    }
}
