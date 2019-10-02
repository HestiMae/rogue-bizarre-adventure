package game;

import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.Item;

/**
 * A class that represents corpses
 */
public class Corpse extends Item implements Sellable, Edible
{
    public static final int FOOD_VALUE = 50;
    public static final int COST = 50;
    Actor type;

    /***
     * Constructor.
     *  @param name the name of the corpse
     * @param type the type of Actor the corpse is of (e.g. Protoceratops)
     */
    public Corpse(String name, Actor type)
    {
        super(name, 'x', true);
        this.type = type;
    }


    @Override
    public int getFoodValue()
    {
        return FOOD_VALUE;
    }


    @Override
    public FoodType getFoodType()
    {
        return FoodType.MEAT;
    }

    @Override
    public int getValue()
    {
        return COST;
    }

    @Override
    public Item copyItem()
    {
        return new Corpse(this.name, this.type);
    }
}
