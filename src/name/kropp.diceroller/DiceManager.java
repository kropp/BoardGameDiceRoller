package name.kropp.diceroller;

import android.content.res.Resources;
import android.content.res.XmlResourceParser;
import android.graphics.Color;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.Random;

/**
 * Created by IntelliJ IDEA.
 * User: kropp
 */
public class DiceManager {
    private Random myRandom;
    private static DiceManager ourInstance;

    private DiceManager() {
        myRandom = new Random(System.currentTimeMillis());
    }

    public static DiceManager getInstance(Resources resources) {
        if (ourInstance == null) {
            ourInstance = new DiceManager();
/*
            try {
                new GamesXmlParser(ourInstance, DiceManager.getInstance(resources)).parseXml(resources.getXml(R.xml.dicesets));
            } catch (IOException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            } catch (XmlPullParserException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
*/
        }
        return ourInstance;
    }
    
    public Die createDie(String type, String color) {
        Die die = null;
        if (type.startsWith("d")) {
            int sides = Integer.valueOf(type.substring(1));

            int dieColor = Color.WHITE;
            if (color != null)
                dieColor = Color.parseColor(color);
            int faceColor = dieColor != Color.BLACK ? Color.BLACK : Color.WHITE;

            die = new SimpleDie(sides, getNextSeed(), dieColor, faceColor);
        } else if (type.equals("catan_cities_knights_event_die")) {
            die = new SettlersOfCatanCitiesAndKnightsEventDie(getNextSeed());
        }
        return die;
    }
    
    private long getNextSeed() {
        return myRandom.nextLong();
    }
}
