package name.kropp.diceroller.dice;

import name.kropp.diceroller.dice.sum.DiceSumNotificationMaker;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: kropp
 */
public class DiceSet {
    private final List<Die> myDices = new LinkedList<Die>();
    
    private final String myId;
    private final String myName;
    private final DiceSumNotificationMaker myNotificationMaker;

    public DiceSet(String id, String name, DiceSumNotificationMaker notificationMaker) {
        myId = id;
        myName = name;
        myNotificationMaker = notificationMaker;
    }

    public String getId() {
        return myId;
    }

    public String getName() {
        return myName;
    }

    public void addDie(Die die) {
        myDices.add(die);
    }

    public void rollAll() {
        for (Die die : myDices) {
            die.roll();
        }
    }

    public List<Die> getDice() {
        return myDices;
    }

    public int getSum() {
        int result = 0;
        for (Die die : myDices) {
            if (die.countsInSum())
            {
                result += die.getCurrentValue();
            }
        }
        return result;
    }

    public String getNotification() {
        return myNotificationMaker.getNotification(this);
    }

    public String getHistoryString() {
        return myNotificationMaker.getHistoryString(this);
    }
}