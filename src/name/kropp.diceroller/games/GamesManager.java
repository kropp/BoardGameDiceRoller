package name.kropp.diceroller.games;

import android.content.Context;
import android.content.res.Resources;
import name.kropp.diceroller.R;
import name.kropp.diceroller.dice.DiceManager;
import name.kropp.diceroller.dice.DiceSet;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: kropp
 */
public class GamesManager {
    private static GamesManager ourInstance;

    private ArrayList<Game> myGames = new ArrayList<Game>();
    private int mySelected;
    private DiceSet mySelectedSet;

    private GamesManager() {
    }

    public static GamesManager getInstance(Context context) {
        if (ourInstance == null) {
            ourInstance = new GamesManager();
            try {
                Resources resources = context.getResources();
                new GamesXmlParser(ourInstance, DiceManager.getInstance(resources), context).parseXml(resources.getXml(R.xml.dicesets));
            } catch (IOException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            } catch (XmlPullParserException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
        }
        return ourInstance;
    }

    public void addGame(Game game) {
        myGames.add(game);
        Collections.sort(myGames);
    }

    public List<Game> getGames() {
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
