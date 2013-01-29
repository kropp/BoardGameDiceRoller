package name.kropp.diceroller.activities;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ListView;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;
import name.kropp.diceroller.R;
import name.kropp.diceroller.games.GamesManager;
import name.kropp.diceroller.games.StatsManager;
import name.kropp.diceroller.settings.VersionManager;

public class MyMainActivity extends SherlockFragmentActivity {
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
        if (getResources().getConfiguration().orientation != Configuration.ORIENTATION_LANDSCAPE || !isXLargeScreen())
            menu.add(R.string.choose_game).setIcon(android.R.drawable.ic_menu_agenda).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item) {
                    showDialog(GAMES_DIALOG_ID);
                    return true;
                }
            }).setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);

        final SherlockFragmentActivity activity = this;

        menu.add(R.string.history_header).setIcon(android.R.drawable.ic_menu_recent_history).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                startActivity(new Intent(activity, StatsActivity.class));
                return true;
            }
        }).setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);

        menu.add(R.string.options).setIcon(android.R.drawable.ic_menu_preferences).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                openOptions();
                return true;
            }
        }).setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);

        menu.add(R.string.donate).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.app_url))));
                return true;
            }
        }).setShowAsActionFlags(MenuItem.SHOW_AS_ACTION_WITH_TEXT | MenuItem.SHOW_AS_ACTION_IF_ROOM);

        menu.add(R.string.about).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                showDialog(ABOUT_DIALOG_ID);
                return true;
            }
        }).setShowAsAction(MenuItem.SHOW_AS_ACTION_WITH_TEXT | MenuItem.SHOW_AS_ACTION_IF_ROOM);

        return true;
    }

    private boolean isXLargeScreen() {
        return (getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) > Configuration.SCREENLAYOUT_SIZE_LARGE;
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
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle(R.string.about_title);
        builder.setMessage(R.string.about_text);

        final Uri uri = Uri.parse(getString(R.string.app_url));

        builder.setPositiveButton(R.string.donate, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                startActivity(new Intent(Intent.ACTION_VIEW, uri));
            }
        });

        builder.setNegativeButton(android.R.string.cancel, null);

        builder.setCancelable(true);

        Dialog dialog = builder.create();
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
