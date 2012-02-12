package name.kropp.diceroller;

/**
 * Created by IntelliJ IDEA.
 * User: kropp
 */
public final class PredefinedSets {
    private PredefinedSets() {
    }

    public static void init(GamesManager gamesManager) {
        gamesManager.addGame(createSimpleDiceGame());
        gamesManager.addGame(createStoneAgeGame());
        gamesManager.addGame(createSettlersOfCatanCitiesAndKnightsGame());
    }

    private static Game createSimpleDiceGame() {
        Game result = new Game("1d6_4d6", "6-sided Dice");

        for(int i = 1; i <= 4; i++) {
            result.addDiceSet(createSimpleDiceSet(i, false));
        }

        return result;
    }

    private static DiceSet createSimpleDiceSet(int numberOfDice, boolean shortName) {
        String name = shortName ?
                String.valueOf(numberOfDice) :
                String.format("%dd6", numberOfDice);
        DiceSet set = new DiceSet(String.format("%dd6", numberOfDice), name);

        long seed = System.currentTimeMillis();

        for (int i = 0; i < numberOfDice; i++) {
            seed *= System.currentTimeMillis();
            set.addDice(new SimpleDie(6, seed));
        }

        return set;
    }

    private static Game createStoneAgeGame() {
        Game result = new Game("stone_age", "Stone Age");
        
        for(int i = 1; i <= 7; i++) {
            result.addDiceSet(createSimpleDiceSet(i, true));
        }
        
        return result;
    }
    
    private static Game createSettlersOfCatanCitiesAndKnightsGame() {
        Game result = new Game("catan_cities_knights", "Settlers of Catan: Cities & Knights");
        result.addDiceSet(createSettlersOfCatanCitiesAndKnightsDiceSet());
        return result;
    }

    private static DiceSet createSettlersOfCatanCitiesAndKnightsDiceSet() {
        DiceSet set = new DiceSet("catan_cities_knights", "Settlers of Catan: Cities & Knights");

        long seed = System.currentTimeMillis();
        set.addDice(new SimpleDie(6, seed));
        set.addDice(new RedDie6(seed * System.currentTimeMillis()));
        set.addDice(new SettlersOfCatanCitiesAndKnightsEventDie(seed * seed * System.currentTimeMillis()));

        return set;
    }
}
