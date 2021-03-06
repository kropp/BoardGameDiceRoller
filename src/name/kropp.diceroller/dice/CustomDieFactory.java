package name.kropp.diceroller.dice;

import name.kropp.diceroller.dice.strategies.CustomDieDrawStrategy;
import name.kropp.diceroller.dice.strategies.DieDrawStrategy;

/**
 * Created by IntelliJ IDEA.
 * User: kropp
 */
public class CustomDieFactory implements DieFactory {
    private final int mySides;
    private final String[] myFaceImages;

    public CustomDieFactory(int sides, Boolean countsInSum) {
        mySides = sides;
        myFaceImages = new String[mySides];
    }

    @Override
    public Die createDie(long seed, int dieColor, int color) {
        return new CustomDie(mySides, seed, dieColor, color, myFaceImages, new CustomDieDrawStrategy());
    }

    @Override
    public DieDrawStrategy getDrawStrategy() {
        return null;
    }

    public void setFaceImage(int face, String image) {
        if (0 <= face && face < myFaceImages.length)
            myFaceImages[face] = image;
    }
}
