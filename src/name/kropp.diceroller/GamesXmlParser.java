package name.kropp.diceroller;

import android.content.res.XmlResourceParser;
import android.graphics.Color;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.Random;

/**
 * Created by IntelliJ IDEA.
 * User: kropp
 */
public class GamesXmlParser {
    private GamesManager myGamesManager;
    private Game myGame;
    private DiceSet myDiceSet;
    private Random myRandom;

    public GamesXmlParser(GamesManager gamesManager) {
        myGamesManager = gamesManager;
        myRandom = new Random(System.currentTimeMillis());
    }

    public void parseXml(XmlResourceParser xml) throws IOException, XmlPullParserException {
        while (xml.getEventType() != XmlPullParser.END_DOCUMENT) {
            xml.next();
            if (xml.getEventType() == XmlPullParser.START_TAG) {
                String name = xml.getName();
                if (name.equals("game")) {
                    startGame(xml);
                } else if (name.equals("diceset")) {
                    startDiceSet(xml);
                } else if (name.equals("die")) {
                    startDie(xml);
                }
            }
        }
    }

    private void startDie(XmlResourceParser xml) {
        String type = xml.getAttributeValue(null, "type");
        Die die = null;
        if (type.startsWith("d")) {
            int sides = Integer.valueOf(type.substring(1));

            String color = xml.getAttributeValue(null, "color");
            int dieColor = Color.WHITE;
            if (color != null)
                dieColor = Color.parseColor(color);
            int faceColor = dieColor != Color.BLACK ? Color.BLACK : Color.WHITE;

            die = new SimpleDie(sides, getNextSeed(), dieColor, faceColor);
        } else if (type.equals("catan_cities_knights_event_die")) {
            die = new SettlersOfCatanCitiesAndKnightsEventDie(getNextSeed());
        }
        if (die != null) {
            die.roll();
            myDiceSet.addDie(die);
        }
    }

    private long getNextSeed() {
        return myRandom.nextLong();
    }

    private void startDiceSet(XmlResourceParser xml) {
        myDiceSet = new DiceSet(xml.getAttributeValue(null, "id"), xml.getAttributeValue(null, "name"));
        myGame.addDiceSet(myDiceSet);
    }


    private void startGame(XmlResourceParser xml) {
        Version version = Version.LITE;
        if (xml.getAttributeCount() > 2 && xml.getAttributeValue(null, "version").equals("full"))
            version = Version.FULL;

        myGame = new Game(xml.getAttributeValue(null, "id"), xml.getAttributeValue(null, "name"), version);
        myGamesManager.addGame(myGame);
    }
}
