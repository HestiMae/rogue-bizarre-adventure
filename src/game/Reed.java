package game;

import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.Ground;
import edu.monash.fit2099.engine.Location;

import java.util.Random;

public class Reed extends Ground implements Edible
{
    private static final char DISPLAY_CHAR = '|';
    private static final int FOOD_VALUE = 5;
    private static final int OVERCROWDED = 6;
    private static final double FISH_CHANCE = 0.1;
    private Random rand;
    /**
     * Constructor.
     */
    public Reed()
    {
        super(DISPLAY_CHAR);
        addSkill(PassableTerrain.WATER);
        rand = new Random();
    }

    @Override
    public String getName()
    {
        return "Reed";
    }

    @Override
    public int getFoodValue()
    {
        return FOOD_VALUE;
    }

    @Override
    public FoodType getFoodType()
    {
        return FoodType.PLANT;
    }

    @Override
    public boolean canActorEnter(Actor actor) { return actor.hasSkill(PassableTerrain.WATER); }

    @Override
    public void tick(Location location)
    {
        super.tick(location);
        if (location.getExits().stream().filter(x -> x.getDestination().getGround() instanceof Reed).count() > OVERCROWDED)
        {
            location.setGround(new Water());
        }
        else if (rand.nextFloat() < FISH_CHANCE && !location.containsAnActor())
        {
            location.addActor(new Fish("Fish"));
        }
    }
}
