package name.kropp.diceroller;

import android.app.Dialog;
import android.app.TabActivity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TabHost;

public class MyMainActivity extends TabActivity {
    static final int ABOUT_DIALOG_ID = 1;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        Resources resources = getResources();
        TabHost tabHost = getTabHost();
        TabHost.TabSpec spec;
        Intent intent;

        intent = new Intent().setClass(this, DiceRollActivity.class);
        spec = tabHost.newTabSpec("rolldice").setIndicator(resources.getString(R.string.rolldice_tab_name),
                resources.getDrawable(R.drawable.ic_tab_main))
                .setContent(intent);
        tabHost.addTab(spec);

        intent = new Intent().setClass(this, StatsActivity.class);
        spec = tabHost.newTabSpec("stats").setIndicator(resources.getString(R.string.stats_tab_name),
                resources.getDrawable(R.drawable.ic_tab_stats))
                .setContent(intent);
        tabHost.addTab(spec);

        intent = new Intent().setClass(this, GamesActivity.class);
        spec = tabHost.newTabSpec("sets").setIndicator(resources.getString(R.string.games_tab_name),
                resources.getDrawable(R.drawable.ic_tab_sets))
                .setContent(intent);
        tabHost.addTab(spec);

        tabHost.setCurrentTab(0);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.options:
                openOptions();
                return true;
            case R.id.about:
                showDialog(ABOUT_DIALOG_ID);
                return true;
            case R.id.clearhistory:
                StatsManager.getInstance(getResources()).clear();

                if (getTabHost().getCurrentTab() == 1) { // reload stats view
                    Intent intent = new Intent(this, MyMainActivity.class);
                    intent.putExtra("tab", "stats");
                    startActivity(intent);
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        if (id == ABOUT_DIALOG_ID) {
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
        getTabHost().setCurrentTab(index);
    }
}
