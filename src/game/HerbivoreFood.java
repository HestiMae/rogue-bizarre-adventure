package game;

import edu.monash.fit2099.engine.Item;

/**
 * Class representing Herbivore food.
 */
public class HerbivoreFood extends Item implements Sellable, Edible
{

    private static final int FOOD_VALUE = 50;
    private static final int COST = 20;

    /**
     * Constructor for herbivore food.
     */
    public HerbivoreFood()
    {
        super("herbivore food", 'h', true);
    }

    @Override
    public int getFoodValue()
    {
        return FOOD_VALUE; // Max metabolise level for herbivore currently is 50
    }

    @Override
    public FoodType getFoodType()
    {
        return FoodType.PLANT;
    }

    @Override
    public int getValue()
    {
        return COST;
    }

    @Override
    public Item copyItem()
    {
        return new HerbivoreFood();
    }
}
