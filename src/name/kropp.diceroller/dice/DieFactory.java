package name.kropp.diceroller.dice;

/**
 * Created by IntelliJ IDEA.
 * User: kropp
 */
public interface DieFactory {
    Die createDie(long seed, int dieColor, int color);
}
