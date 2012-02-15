package name.kropp.diceroller;

import android.content.res.Resources;
import android.content.res.XmlResourceParser;
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
    private int mySelected;

    private GamesManager() {
    }

    public static GamesManager getInstance(Resources resources) {
        if (ourInstance == null) {
            ourInstance = new GamesManager();
            try {
                new GamesXmlParser(ourInstance).parseXml(resources.getXml(R.xml.dicesets));
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
    }

    public List<Game> getGames() {
        return myGames;
    }

    public void setSelected(int index) {
        if (0 <= index && index < myGames.size())
            mySelected = index;
    }

    public void setSelected(String id) {
        int i = 0;
        for (Game game : myGames) {
            if (game.getId().equals(id)) {
                setSelected(i);
            }
            i++;
        }
    }

    public Game getSelectedGame() {
        return myGames.get(mySelected);
    }
}