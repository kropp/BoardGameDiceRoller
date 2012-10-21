package name.kropp.diceroller.activities;

import android.app.Dialog;
import android.app.TabActivity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TabHost;
import name.kropp.diceroller.R;

public class MyMainActivity extends FragmentActivity {
    static final int ABOUT_DIALOG_ID = 1;

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
    }
}
