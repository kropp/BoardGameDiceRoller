package name.kropp.diceroller.dice;

import android.content.res.XmlResourceParser;
import name.kropp.diceroller.dice.strategies.DieDrawStrategy;
import name.kropp.diceroller.dice.strategies.RectFaceDieDrawStrategy;
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
    private CustomDieFactory myDieFactory;
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
                if (name.equals("imageDie")) {
                    startImageDie(xml);
                } else if (name.equals("rangeDie")) {
                    startRangeDie(xml);
                } else if (name.equals("numbersDie")) {
                    startNumbersDie(xml);
                } else if (name.equals("face")) {
                    addFace(xml);
                }
            }
        }
    }

    private void addFace(XmlResourceParser xml) {
        String image = xml.getAttributeValue(null, "image");
        myDieFactory.setFaceImage(myIndex, image);
        myIndex++;
    }

    private void startImageDie(XmlResourceParser xml) {
        String type = xml.getAttributeValue(null, "type");
        String countsInSumAttr = xml.getAttributeValue(null, "countsInSum");
        Boolean countsInSum = countsInSumAttr != null ? Boolean.valueOf(countsInSumAttr) : true;
        myDieFactory = new CustomDieFactory(Integer.valueOf(type.substring(1)), countsInSum);
        myDiceManager.addDieFactory(xml.getAttributeValue(null, "id"), myDieFactory);
        myIndex = 0;
    }

    private void startRangeDie(XmlResourceParser xml) {
        String type = xml.getAttributeValue(null, "type");

        Integer from = Integer.valueOf(xml.getAttributeValue(null, "from"));
        Integer to = Integer.valueOf(xml.getAttributeValue(null, "to"));

        DieDrawStrategy drawStrategy = getDieDrawStrategyByType(type);

        myDiceManager.addDieFactory(xml.getAttributeValue(null, "id"), new CustomRangeDieFactory(from, to, drawStrategy));
    }

    private void startNumbersDie(XmlResourceParser xml) {
        String type = xml.getAttributeValue(null, "type");

        String[] strNumbers = xml.getAttributeValue(null, "numbers").split(",");
        int numbers[] = new int[strNumbers.length];
        for (int i = 0; i < strNumbers.length; i++) {
            numbers[i] = Integer.valueOf(strNumbers[i]);
        }

        DieDrawStrategy drawStrategy = getDieDrawStrategyByType(type);

        myDiceManager.addDieFactory(xml.getAttributeValue(null, "id"), new CustomNumbersDieFactory(numbers, drawStrategy));
    }

    private DieDrawStrategy getDieDrawStrategyByType(String type) {
        if (type.equals("d6"))
            return new RectFaceDieDrawStrategy();

        DieFactory dieFactory = myDiceManager.getDieFactory(type);
        if (dieFactory != null)
            return dieFactory.getDrawStrategy();
        return null;
    }
}