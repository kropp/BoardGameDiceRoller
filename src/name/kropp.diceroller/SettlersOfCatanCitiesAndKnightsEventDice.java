package name.kropp.diceroller;

/**
 * Created by IntelliJ IDEA.
 * User: kropp
 */
public class SettlersOfCatanCitiesAndKnightsEventDice extends Dice6 {

    public SettlersOfCatanCitiesAndKnightsEventDice(long seed) {
        super(seed);
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
}