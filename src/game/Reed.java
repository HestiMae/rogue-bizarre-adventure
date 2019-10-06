package game;

import edu.monash.fit2099.engine.Ground;
import edu.monash.fit2099.engine.Location;

public class Reed extends Ground implements Edible
{
    private static final char DISPLAY_CHAR = '|';
    private static final int FOOD_VALUE = 5;
    private static final int OVERCROWDED = 6;
    /**
     * Constructor.
     */
    public Reed()
    {
        super(DISPLAY_CHAR);
        addSkill(PassableTerrain.WATER);
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
    public void tick(Location location) //TODO: Generate fish
    {
        super.tick(location);
        if (location.getExits().stream().filter(x -> x.getDestination().getGround() instanceof Reed).count() > OVERCROWDED)
        {
            location.setGround(new Water());
        }
    }
}
