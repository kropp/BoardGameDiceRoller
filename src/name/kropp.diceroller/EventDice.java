package name.kropp.diceroller;

/**
 * Created by IntelliJ IDEA.
 * User: kropp
 */
public class EventDice {
    private Dice myDice;

    public EventDice(Dice dice) {
        myDice = dice;
    }

    public Event roll() {
        switch (myDice.roll()) {
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
}
