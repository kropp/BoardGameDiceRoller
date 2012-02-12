package name.kropp.diceroller;

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

    public static GamesManager getInstance() {
        if (ourInstance == null) {
            ourInstance = new GamesManager();
            PredefinedSets.init(ourInstance);
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
