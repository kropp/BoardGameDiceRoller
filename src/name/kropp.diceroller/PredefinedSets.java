package name.kropp.diceroller;

/**
 * Created by IntelliJ IDEA.
 * User: kropp
 */
public final class PredefinedSets {
    private PredefinedSets() {
    }

    public static void init(SetsManager setsManager) {
        DiceSet set = new DiceSet("Settlers of Catan: Cities & Knights");

        long seed = System.currentTimeMillis();
        set.addDice(new Dice6(seed));
        set.addDice(new RedDice6(seed * System.currentTimeMillis()));
        set.addDice(new SettlersOfCatanCitiesAndKnightsEventDice(seed * seed * System.currentTimeMillis()));

        setsManager.addSet(set);
    }
}
