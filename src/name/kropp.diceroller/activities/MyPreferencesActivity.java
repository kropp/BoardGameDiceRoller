package name.kropp.diceroller.activities;

import android.content.SharedPreferences;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;
import android.preference.RingtonePreference;
import name.kropp.diceroller.R;
import name.kropp.diceroller.settings.PreferenceNames;

/**
 * Created by IntelliJ IDEA.
 * User: kropp
 */
public class MyPreferencesActivity extends PreferenceActivity implements Preference.OnPreferenceChangeListener {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);
    }

    @Override
    public boolean onPreferenceChange(Preference preference, Object newValue) {
        updateRingtoneSummary((RingtonePreference) preference, (String) newValue);
        return true;
    }

    private void updateRingtoneSummary(RingtonePreference preference, String uri) {
        Ringtone ringtone = uri != null && uri.length() > 0 ? RingtoneManager.getRingtone(this, Uri.parse(uri)) : null;
        if (ringtone != null)
            preference.setSummary(ringtone.getTitle(this));
        else
            preference.setSummary(getResources().getString(R.string.silent));
    }

    @Override
    protected void onResume() {
        super.onResume();

        // A patch to overcome OnSharedPreferenceChange not being called by RingtonePreference bug
        RingtonePreference pref = (RingtonePreference) findPreference(PreferenceNames.Notification);
        pref.setOnPreferenceChangeListener(this);

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        String uri = preferences.getString(PreferenceNames.Notification, "");

        updateRingtoneSummary(pref, uri);
    }
}
