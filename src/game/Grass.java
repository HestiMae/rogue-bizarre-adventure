package game;

import edu.monash.fit2099.engine.Ground;

public class Grass extends Ground implements Edible
{

    /**
     * Constructor.
     */
    public Grass()
    {
        super('v');
    }

    @Override
    public int getFoodValue()
    {
        return 5;
    }

    @Override
    public FoodType getFoodType()
    {
        return FoodType.PLANT;
    }
}
