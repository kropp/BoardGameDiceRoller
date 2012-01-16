package name.kropp.diceroller;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class MyMainActivity extends Activity
{
    private Dice6 myWhiteDice;
    private RedDice6 myRedDice;
    private SettlersOfCatanCitiesAndKnightsEventDice myEventDice;
    private ShakeListener myShaker;

    public MyMainActivity() {
        long seed = System.currentTimeMillis();
        myWhiteDice = new Dice6(seed);
        myRedDice = new RedDice6(seed * System.currentTimeMillis());
        myEventDice = new SettlersOfCatanCitiesAndKnightsEventDice(seed * seed * System.currentTimeMillis());
    }

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        final Button button = (Button) findViewById(R.id.button_id);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                RollDice();
            }
        });

        myShaker = new ShakeListener(this);
        myShaker.setOnShakeListener(new ShakeListener.OnShakeListener() {
            public void onShake() {
                RollDice();
            }
        });

        final TextView textView = (TextView) findViewById(R.id.text);
        textView.setTextSize(20);
    }

    @Override
    public void onResume() {
        myShaker.resume();
        super.onResume();
    }

    @Override
    public void onPause() {
        myShaker.pause();
        super.onPause();
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
            return true;
        default:
            return super.onOptionsItemSelected(item);
        }
    }

    private void openOptions() {
        Intent myIntent = new Intent(this, MyPreferencesActivity.class);
        this.startActivity(myIntent);
    }

    private void RollDice() {
        myWhiteDice.roll();
        myRedDice.roll();
        myEventDice.roll();

        updateDiceImage(R.id.white_dice, myWhiteDice.getIconId());
        updateDiceImage(R.id.red_dice, myRedDice.getIconId());
        updateDiceImage(R.id.event_dice, myEventDice.getIconId());

        final TextView textView = (TextView) findViewById(R.id.text);

        String[] lines = textView.getText().toString().split("\n", -1);
        StringBuilder old = new StringBuilder();
        for (int i = 0; i < 5; i++)
            old.append(lines[i]).append("\n");

        CharSequence newText = String.format("%d [%d] = %d %s\n%s", myWhiteDice.getCurrentValue(), myRedDice.getCurrentValue(), myWhiteDice.getCurrentValue() + myRedDice.getCurrentValue(), myEventDice.getCurrentEvent().toString(), old.toString());
        textView.setText(newText);
    }

    private void updateDiceImage(int dice, int icon) {
        final ImageView imageView = (ImageView) findViewById(dice);
        imageView.setImageResource(icon);
    }
}
