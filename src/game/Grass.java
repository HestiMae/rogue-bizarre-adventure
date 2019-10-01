package game;

import edu.monash.fit2099.engine.Ground;

/**
 * Class representing grass
 */
public class Grass extends Ground implements Edible
{

    /**
     * Constructor for grass.
     */
    public Grass()
    {
        super('v');
    }

    /**
     * Gets the number of hunger points restored when eaten
     * @return hunger points restored when eaten
     */
    @Override
    public int getFoodValue()
    {
        return 5;
    }

    /**
     * Gets the type of food that grass is
     * @return enum food type of grass
     */
    @Override
    public FoodType getFoodType()
    {
        return FoodType.PLANT;
    }

    @Override
    public String getName()
    {
        return "Grass";
    }
}
