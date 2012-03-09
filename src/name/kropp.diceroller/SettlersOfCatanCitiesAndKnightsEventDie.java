package name.kropp.diceroller;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

/**
 * Created by IntelliJ IDEA.
 * User: kropp
 */
public class SettlersOfCatanCitiesAndKnightsEventDie extends SimpleDie {

    public SettlersOfCatanCitiesAndKnightsEventDie(long seed) {
        super(6, seed, 0, 0);
    }

    public Event getCurrentEvent()
    {
        switch (getCurrentValue()) {
            case 1:
                return Event.YELLOW_CITY;
            case 2:
                return Event.BLUE_CITY;
            case 3:
                return Event.GREEN_CITY;
            default:
                return Event.PIRATES;
        }
    }

    @Override
    public View getCurrentView(Context context) {
        ImageView image = new ImageView(context);
        image.setImageDrawable(context.getResources().getDrawable(getIconId()));
        return image;
    }

    public int getIconId() {
        switch (getCurrentEvent())
        {
            case BLUE_CITY: return R.drawable.bluecity;
            case YELLOW_CITY: return R.drawable.yellowcity;
            case GREEN_CITY: return R.drawable.greencity;
            case PIRATES: return R.drawable.pirates;
            default: return R.drawable.pirates;
        }
    }

    @Override
    public String toString() {
        return String.format("<img src=\"%d\">", getIconId());
    }
}