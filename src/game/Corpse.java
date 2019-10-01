package game;

import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.Item;

/**
 * A class that represents corpses
 */
public class Corpse extends Item implements Sellable, Edible
{
    Actor type;

    /***
     * Constructor.
     *  @param name the name of the corpse
     */
    public Corpse(String name, Actor type)
    {
        super(name, 'x', true);
        this.type = type;
    }

    /**
     * Gets the number of hunger points restored when eaten
     * @return hunger points restored when eaten
     */
    @Override
    public int getFoodValue()
    {
        return 50;
    }

    /**
     * Gets the type of food that corpse is
     * @return enum food type of corpse
     */
    @Override
    public FoodType getFoodType()
    {
        return FoodType.MEAT;
    }

    /**
     * Gets the monetary value of the corpse for buying and selling
     * @return monetary value of corpse
     */
    @Override
    public int getValue()
    {
        return 50;
    }

    @Override
    public Item copyItem()
    {
        return new Corpse(this.name, this.type);
    }
}
