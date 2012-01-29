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

        Resources res = getResources();
        TabHost tabHost = getTabHost();
        TabHost.TabSpec spec;
        Intent intent;

        intent = new Intent().setClass(this, DiceRollActivity.class);
        spec = tabHost.newTabSpec("rolldice").setIndicator("Roll dice!",
                res.getDrawable(R.drawable.ic_tab_main))
                .setContent(intent);
        tabHost.addTab(spec);

        intent = new Intent().setClass(this, StatsActivity.class);
        spec = tabHost.newTabSpec("Stats").setIndicator("Stats",
                res.getDrawable(R.drawable.ic_tab_stats))
                .setContent(intent);
        tabHost.addTab(spec);

        intent = new Intent().setClass(this, SetsActivity.class);
        spec = tabHost.newTabSpec("sets").setIndicator("Sets",
                res.getDrawable(R.drawable.ic_tab_sets))
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
        dialog.setTitle("About Board Game Dice Roller");
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(true);
/*
        ImageView image = (ImageView) dialog.findViewById(R.id.image);
        image.setImageResource(R.drawable.android);
*/
        return dialog;
    }

    private void openOptions() {
        this.startActivity(new Intent(this, MyPreferencesActivity.class));
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        getTabHost().setCurrentTab(0);
    }
}
