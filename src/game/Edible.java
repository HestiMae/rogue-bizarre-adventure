package game;

public interface Edible
{
    /**
     * Gets the number of metabolise points restored when eaten
     * @return the number of metabolise points restored when eaterd
     */
    int getFoodValue();

    /**
     * Returns the food type of an edible (PLANT or MEAT)
     * @return FoodType, PLANT or MEAT
     */
    FoodType getFoodType();
}
