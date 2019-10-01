package game;

import edu.monash.fit2099.engine.Item;

public class CarnivoreFood extends Item implements Edible, Sellable
{
    private static final boolean PORTABLE = true;
    private static char DISPLAY_CHAR = 'c';

    /**
     * No parameter constructor for simplicity's sake
     */
    public CarnivoreFood()
    {
        super("Carnivore Food", DISPLAY_CHAR, PORTABLE);
    }

    @Override
    public int getFoodValue()
    {
        return 100;
    }

    @Override
    public FoodType getFoodType()
    {
        return FoodType.MEAT;
    }

    @Override
    public int getValue()
    {
        return 100;
    }

    @Override
    public Item copyItem()
    {
        return new CarnivoreFood();
    }
}
