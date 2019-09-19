package game;

import edu.monash.fit2099.engine.Item;

public class HerbivoreFood extends Item implements Sellable, Edible
{
    /***
     * Constructor.
     */
    public HerbivoreFood()
    {
        super("herbivore food", 'h', true);
    }

    @Override
    public int getFoodValue()
    {
        return 50; // Max hunger level for herbivore currently is 50
    }

    @Override
    public FoodType getFoodType()
    {
        return FoodType.PLANT;
    }

    @Override
    public int getValue()
    {
        return 20;
    }
}
