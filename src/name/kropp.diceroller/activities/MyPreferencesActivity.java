package name.kropp.diceroller.activities;

import android.os.Bundle;
import android.preference.PreferenceActivity;
import name.kropp.diceroller.R;

/**
 * Created by IntelliJ IDEA.
 * User: kropp
 */
public class MyPreferencesActivity extends PreferenceActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);
    }
}
