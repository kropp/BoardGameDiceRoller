package name.kropp.diceroller;

/**
 * Created by IntelliJ IDEA.
 * User: kropp
 */
public final class PredefinedSets {
    private PredefinedSets() {
    }

    public static void init(SetsManager setsManager) {
        setsManager.addSet(createSimpleDiceSet(1));
        setsManager.addSet(createSimpleDiceSet(2));
        setsManager.addSet(createSimpleDiceSet(3));
        setsManager.addSet(createSimpleDiceSet(4));
        setsManager.addSet(createSimpleDiceSet(5));
        setsManager.addSet(createSimpleDiceSet(6));
        setsManager.addSet(createSimpleDiceSet(7));

        setsManager.addSet(createSettlersOfCatanCitiesAndKnightsDiceSet());
    }

    private static DiceSet createSimpleDiceSet(int numberOfDice) {
        DiceSet set = new DiceSet(String.format("%dd6", numberOfDice), String.format("%d six-sided dice (%dd6)", numberOfDice, numberOfDice));

        long seed = System.currentTimeMillis();

        for (int i = 0; i < numberOfDice; i++) {
            seed *= System.currentTimeMillis();
            set.addDice(new Die6(seed));
        }

        return set;
    }

    private static DiceSet createSettlersOfCatanCitiesAndKnightsDiceSet() {
        DiceSet set = new DiceSet("catan_cities_knights", "Settlers of Catan: Cities & Knights");

        long seed = System.currentTimeMillis();
        set.addDice(new Die6(seed));
        set.addDice(new RedDie6(seed * System.currentTimeMillis()));
        set.addDice(new SettlersOfCatanCitiesAndKnightsEventDie(seed * seed * System.currentTimeMillis()));

        return set;
    }
}
