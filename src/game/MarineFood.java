package game;

import edu.monash.fit2099.engine.Item;

public class MarineFood extends Item implements Sellable, Edible
{
    private static final int FOOD_VALUE = 50;
    private static final int COST = 500;

    /***
     * Constructor.
     */
    public MarineFood()
    {
        super("Marine food", 'm', true);
    }


    @Override
    public Item copyItem()
    {
        return new MarineFood();
    }

    @Override
    public int getFoodValue()
    {
        return FOOD_VALUE;
    }

    @Override
    public FoodType getFoodType()
    {
        return FoodType.MARINE;
    }

    @Override
    public int getValue()
    {
        return COST;
    }
}
