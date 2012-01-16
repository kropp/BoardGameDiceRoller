package name.kropp.diceroller;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class MyMainActivity extends Activity
{
    private Dice myWhiteDice;
    private Dice myRedDice;
    private EventDice myEventDice;

    public MyMainActivity() {
        long seed = System.currentTimeMillis();
        myWhiteDice = new Dice(seed);
        myRedDice = new Dice(seed * System.currentTimeMillis());
        myEventDice = new EventDice(new Dice(seed * seed * System.currentTimeMillis()));
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

        final TextView textView = (TextView) findViewById(R.id.text);
        textView.setTextSize(20);
    }

    private void RollDice() {
        int white = myWhiteDice.roll();
        int red = myRedDice.roll();
        Event event = myEventDice.roll();

        updateDiceImage(R.id.white_dice, getWhiteIconId(white));
        updateDiceImage(R.id.red_dice, getRedIconId(red));
        updateDiceImage(R.id.event_dice, getEventIconId(event));

        final TextView textView = (TextView) findViewById(R.id.text);

        String[] lines = textView.getText().toString().split("\n", -1);
        StringBuilder old = new StringBuilder();
        for (int i = 0; i < 5; i++)
            old.append(lines[i]).append("\n");

        CharSequence newText = white + " [" + red + "] = " + (white+red) + " " + event + "\n" + old.toString();
        textView.setText(newText);
    }

    private int getEventIconId(Event event) {
        switch (event)
        {
            case BLUE_CITY: return R.drawable.bluecity;
            case YELLOW_CITY: return R.drawable.yellowcity;
            case GREEN_CITY: return R.drawable.greencity;
            case PIRATES: return R.drawable.pirates;
            default: return R.drawable.pirates;
        }
    }

    private int getRedIconId(int i) {
        switch (i)
        {
            case 1: return R.drawable.r1;
            case 2: return R.drawable.r2;
            case 3: return R.drawable.r3;
            case 4: return R.drawable.r4;
            case 5: return R.drawable.r5;
            case 6: return R.drawable.r6;
            default: return R.drawable.r1;
        }
    }

    private int getWhiteIconId(int i) {
        switch (i)
        {
            case 1: return R.drawable.w1;
            case 2: return R.drawable.w2;
            case 3: return R.drawable.w3;
            case 4: return R.drawable.w4;
            case 5: return R.drawable.w5;
            case 6: return R.drawable.w6;
            default: return R.drawable.w1;
        }
    }

    private void updateDiceImage(int dice, int icon) {
        final ImageView imageView = (ImageView) findViewById(dice);
        imageView.setImageResource(icon);
    }
}
