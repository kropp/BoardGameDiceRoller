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
    private Game myGame;
    private DiceSet myDiceSet;

    public GamesXmlParser(GamesManager gamesManager) {
        myGamesManager = gamesManager;
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
        if (type.equals("d6")) {
            die = new SimpleDie(6, System.currentTimeMillis());
        } else if (type.equals("catan_cities_knights_event_die")) {
            die = new SettlersOfCatanCitiesAndKnightsEventDie(System.currentTimeMillis());
        }
        if (die != null)
            myDiceSet.addDie(die);
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
