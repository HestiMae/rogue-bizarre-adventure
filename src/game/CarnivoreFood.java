package game;

import edu.monash.fit2099.engine.Item;

public class CarnivoreFood extends Item implements Edible, Sellable
{
    private static final boolean PORTABLE = true;
    private static char DISPLAY_CHAR = 'c';

    /***
     * Constructor.
     *  @param name the name of this Item
     * @param displayChar the character to use to represent this item if it is on the ground
     * @param portable true if and only if the Item can be picked up
     */
    public CarnivoreFood(String name, char displayChar, boolean portable)
    {
        super(name, displayChar, portable);
    }

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
}
