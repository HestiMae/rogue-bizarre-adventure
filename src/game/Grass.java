package game;

import edu.monash.fit2099.engine.Actor;
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
        addSkill(PassableTerrain.LAND);
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

    @Override
    public String getName()
    {
        return "Grass";
    }

    @Override
    public boolean canActorEnter(Actor actor)
    {
        return actor.hasSkill(PassableTerrain.LAND);
    }
}
