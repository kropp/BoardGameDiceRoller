package name.kropp.diceroller;

import android.content.res.XmlResourceParser;
import name.kropp.diceroller.dice.CustomDieFactory;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.Random;

/**
 * Created by IntelliJ IDEA.
 * User: kropp
 */
public class DiceXmlParser {
    private DiceManager myDiceManager;
    private Random myRandom;
    private CustomDieFactory dieFactory;
    private int myIndex;

    public DiceXmlParser(DiceManager diceManager) {
        myDiceManager = diceManager;
        myRandom = new Random(System.currentTimeMillis());
    }

    public void parseXml(XmlResourceParser xml) throws IOException, XmlPullParserException {
        while (xml.getEventType() != XmlPullParser.END_DOCUMENT) {
            xml.next();
            if (xml.getEventType() == XmlPullParser.START_TAG) {
                String name = xml.getName();
                if (name.equals("die")) {
                    startDie(xml);
                } else if (name.equals("face")) {
                    addFace(xml);
                }
            }
        }
    }

    private void addFace(XmlResourceParser xml) {
        String image = xml.getAttributeValue(null, "image");
        dieFactory.setFaceImage(myIndex, image);
        myIndex++;
    }

    private void startDie(XmlResourceParser xml) {
        String type = xml.getAttributeValue(null, "type");
        String countsInSumAttr = xml.getAttributeValue(null, "countsInSum");
        Boolean countsInSum = countsInSumAttr != null ? Boolean.valueOf(countsInSumAttr) : true;
        dieFactory = new CustomDieFactory(Integer.valueOf(type.substring(1)), countsInSum);
        myDiceManager.addDieFactory(xml.getAttributeValue(null, "id"), dieFactory);
        myIndex = 0;
    }

    private long getNextSeed() {
        return myRandom.nextLong();
    }
}