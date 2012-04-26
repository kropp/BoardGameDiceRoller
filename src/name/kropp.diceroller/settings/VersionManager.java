package name.kropp.diceroller.settings;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import name.kropp.diceroller.R;

/**
 * Created by IntelliJ IDEA.
 * User: kropp
 */
public class VersionManager {
    private static VersionManager ourInstance;

    private VersionManager() {
    }

    public static VersionManager getInstance() {
        if (ourInstance == null)
            ourInstance = new VersionManager();
        return ourInstance;
    }

    public Version getVersion() {
        return Version.LITE;
    }

    public void showBuyDialog(Context context) {
        final AlertDialog dialog = new AlertDialog.Builder(context).create();
        dialog.setIcon(context.getResources().getDrawable(R.drawable.icon));
        dialog.setTitle(context.getString(R.string.upgrade_header));
        dialog.setMessage(context.getString(R.string.smth_available_only_in_full_version));

        dialog.setButton(context.getString(R.string.buy_full_version), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        });

        dialog.setButton2(context.getString(android.R.string.cancel), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialog.cancel();
            }
        });

        dialog.show();
    }
}
