package name.kropp.diceroller.games;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.XmlResourceParser;
import name.kropp.diceroller.dice.DiceManager;
import name.kropp.diceroller.dice.DiceSet;
import name.kropp.diceroller.dice.Die;
import name.kropp.diceroller.dice.RethrowableDiceSet;
import name.kropp.diceroller.settings.Version;
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
    private Context myContext;
    private Game myGame;
    private DiceSet myDiceSet;

    public GamesXmlParser(GamesManager gamesManager, DiceManager diceManager, Context context) {
        myGamesManager = gamesManager;
        myDiceManager = diceManager;
        myContext = context;
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
        String id = xml.getAttributeValue(null, "id");
        Resources resources = myContext.getResources();
        int resId = resources.getIdentifier("_" + id, "string", myContext.getPackageName());
        String name = resId != 0 ? resources.getString(resId) : "";
        int attempts = xml.getAttributeIntValue(null, "rethrow-attempts", 0);
        if (attempts > 0)
            myDiceSet = new RethrowableDiceSet(id, name, attempts);
        else
            myDiceSet = new DiceSet(id, name);
        myGame.addDiceSet(myDiceSet);
    }


    private void startGame(XmlResourceParser xml) {
        Version version = Version.LITE;
        if (xml.getAttributeCount() > 2 && xml.getAttributeValue(null, "version").equals("full"))
            version = Version.FULL;

        String id = xml.getAttributeValue(null, "id");
        Resources resources = myContext.getResources();
        int resId = resources.getIdentifier("_" + id, "string", myContext.getPackageName());
        String name = resId != 0 ? resources.getString(resId) : "";

        myGame = new Game(id, name, version);
        myGamesManager.addGame(myGame);
    }
}