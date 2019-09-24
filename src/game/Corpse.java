package game;

import edu.monash.fit2099.engine.Item;

public class Corpse extends Item implements Sellable, Edible
{
    Dinosaur type;

    /***
     * Constructor.
     *  @param name the name of this Item
     */
    public Corpse(String name, Dinosaur type)
    {
        super(name, 'x', true);
        this.type = type;
    }

    @Override
    public int getFoodValue()
    {
        return 50;
    }

    @Override
    public FoodType getFoodType()
    {
        return FoodType.MEAT;
    }

    @Override
    public int getValue()
    {
        return 50;
    }
}
