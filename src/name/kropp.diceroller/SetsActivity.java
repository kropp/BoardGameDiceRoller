package name.kropp.diceroller;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

/**
 * Created by IntelliJ IDEA.
 * User: kropp
 */
public class SetsActivity extends Activity {
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        TextView textview = new TextView(this);
        textview.setText("This is the Sets tab");
        setContentView(textview);
    }
}