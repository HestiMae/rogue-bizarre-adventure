package game;

import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.Exit;
import edu.monash.fit2099.engine.Ground;
import edu.monash.fit2099.engine.Location;

import java.util.Random;

/**
 * Water ground type.
 */
public class Water extends Ground
{
    private static final char DISPLAY_CHAR = '~';
    private Random rand;
    private static final double GROWTH_CHANCE_LAND = 0.1;
    private static final double GROWTH_CHANCE_WATER = 0.05;
    /**
     * Constructor.
     **/
    public Water()
    {
        super(DISPLAY_CHAR);
        rand = new Random();
        addSkill(PassableTerrain.WATER);
    }

    @Override
    public String getName()
    {
        return "Water";
    }

    @Override
    public boolean canActorEnter(Actor actor)
    {
        return actor.hasSkill(PassableTerrain.WATER); //actors can only enter if they are allowed on water.
    }

    @Override
    public void tick(Location location)
    {
        super.tick(location);
        PassableTerrain reedEnvironment = null;

        if (location.getExits().stream().anyMatch(exit -> exit.getDestination().getGround().hasSkill(PassableTerrain.LAND))) //Checks if adjacent tiles are land
        {
            reedEnvironment = PassableTerrain.LAND;
        }
        else if (rand.nextFloat() < GROWTH_CHANCE_WATER && location.getExits().stream()//checks if adjacent tiles are reeds (only if adjacent tiles are not land)
                .map(Exit::getDestination)
                .map(Location::getGround)
                .anyMatch(x -> x instanceof Reed)
        )
        {
            reedEnvironment = PassableTerrain.WATER;
        }

        if (reedEnvironment == PassableTerrain.LAND && rand.nextFloat() < GROWTH_CHANCE_LAND)
        {
            location.setGround(new Reed());
        }
        else if (reedEnvironment == PassableTerrain.WATER && rand.nextFloat() < GROWTH_CHANCE_WATER)
        {
            location.setGround(new Reed());
        }
    }
}
