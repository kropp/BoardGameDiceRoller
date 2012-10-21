package name.kropp.diceroller.dice;

import android.content.res.Resources;
import android.graphics.Color;
import name.kropp.diceroller.R;
import name.kropp.diceroller.dice.strategies.*;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.Random;

/**
 * Created by IntelliJ IDEA.
 * User: kropp
 */
public class DiceManager {
    private Random myRandom;
    private Dictionary<String, DieFactory> myDiceFactories;

    private static DiceManager ourInstance;

    private DiceManager() {
        myRandom = new Random(System.currentTimeMillis());
        myDiceFactories = new Hashtable<String, DieFactory>();

        myDiceFactories.put("d4", new SimpleDieFactory(4, new TriangleFaceDieDrawStrategy()));
        myDiceFactories.put("d6", new SimpleDieFactory(6, new CubicDieDrawStrategy()));
        myDiceFactories.put("d8", new SimpleDieFactory(8, new TriangleFaceDieDrawStrategy()));
        myDiceFactories.put("d10", new SimpleDieFactory(10, new KiteFaceDieDrawStrategy()));
        myDiceFactories.put("d12", new SimpleDieFactory(12, new PentagonFaceDieDrawStrategy()));
        myDiceFactories.put("d20", new SimpleDieFactory(20, new TriangleFaceDieDrawStrategy()));
    }

    public static DiceManager getInstance(Resources resources) {
        if (ourInstance == null) {
            ourInstance = new DiceManager();
            try {
                new DiceXmlParser(ourInstance).parseXml(resources.getXml(R.xml.dice));
            } catch (IOException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            } catch (XmlPullParserException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
        }
        return ourInstance;
    }

    public Die createDie(String type, String color) {
        DieFactory dieFactory = myDiceFactories.get(type);
        if (dieFactory != null) {
            int dieColor = color != null ? Color.parseColor(color) : Color.WHITE;
            int faceColor = dieColor != Color.BLACK && dieColor != Color.argb(0xff, 0x33, 0x33, 0x33) ? Color.BLACK : Color.WHITE;

            return dieFactory.createDie(getNextSeed(), dieColor, faceColor);
        }
        return null;
    }

    private long getNextSeed() {
        return myRandom.nextLong();
    }

    public void addDieFactory(String type, DieFactory dieFactory) {
        myDiceFactories.put(type, dieFactory);
    }

    public DieFactory getDieFactory(String type) {
        return myDiceFactories.get(type);
    }
}
