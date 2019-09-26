package game;

import edu.monash.fit2099.engine.Item;

public class HerbivoreFood extends Item implements Sellable, Edible
{
    /**
     * Constructor for herbivore food.
     */
    public HerbivoreFood()
    {
        super("herbivore food", 'h', true);
    }

    /**
     * Gets the number of hunger points restored when eaten
     * @return hunger points restored when eaten
     */
    @Override
    public int getFoodValue()
    {
        return 50; // Max hunger level for herbivore currently is 50
    }

    /**
     * Gets the type of food that herbivore food is
     * @return enum food type of herbivore food
     */
    @Override
    public FoodType getFoodType()
    {
        return FoodType.PLANT;
    }

    /**
     * Gets the monetary value of the herbivore food for buying and selling
     * @return monetary value of herbivore food
     */
    @Override
    public int getValue()
    {
        return 20;
    }
}
