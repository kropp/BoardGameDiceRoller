package name.kropp.diceroller;

import android.content.res.XmlResourceParser;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;

/**
 * Created by IntelliJ IDEA.
 * User: kropp
 */
public class GamesXmlParser {
    private GamesManager myGamesManager;
    private DiceManager myDiceManager;
    private Game myGame;
    private DiceSet myDiceSet;

    public GamesXmlParser(GamesManager gamesManager, DiceManager diceManager) {
        myGamesManager = gamesManager;
        myDiceManager = diceManager;
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
        Die die = myDiceManager.createDie(xml.getAttributeValue(null, "type"), xml.getAttributeValue(null, "color"));
        if (die != null) {
            die.roll();
            myDiceSet.addDie(die);
        }
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
