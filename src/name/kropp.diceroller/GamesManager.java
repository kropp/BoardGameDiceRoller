package name.kropp.diceroller;

import android.content.res.Resources;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: kropp
 */
public class GamesManager {
    private static GamesManager ourInstance;

    private ArrayList<Game> myGames = new ArrayList<Game>();
    private ArrayList<Game> myGamesLite = new ArrayList<Game>();
    private int mySelected;
    private DiceSet mySelectedSet;

    private GamesManager() {
    }

    public static GamesManager getInstance(Resources resources) {
        if (ourInstance == null) {
            ourInstance = new GamesManager();
            try {
                new GamesXmlParser(ourInstance, DiceManager.getInstance(resources)).parseXml(resources.getXml(R.xml.dicesets));
            } catch (IOException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            } catch (XmlPullParserException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
        }
        return ourInstance;
    }

    public void addGame(Game game, Version version) {
        myGames.add(game);
        if (version == Version.LITE)
            myGamesLite.add(game);
    }

    public List<Game> getGames(Version version) {
        if (version == Version.LITE)
            return myGamesLite;
        return myGames;
    }

    public void setSelectedGame(int index) {
        if (0 <= index && index < myGames.size())
            mySelected = index;
    }

    public void setSelectedGame(String id) {
        int i = 0;
        for (Game game : myGames) {
            if (game.getId().equals(id)) {
                setSelectedGame(i);
            }
            i++;
        }
    }

    public Game getSelectedGame() {
        return myGames.get(mySelected);
    }

    public DiceSet getSelectedSet() {
        return mySelectedSet;
    }
    
    public void setSelectedSet(String setId) {
        for (DiceSet set : getSelectedGame().getDiceSets()) {
            if (set.getId().equals(setId)) {
                mySelectedSet = set;
                break;
            }
        }
    }
}
