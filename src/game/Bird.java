package game;

public class Bird extends SimpleCreature
{
    private static int HITPOINTS = 10;
    private static int FOOD_VALUE = 10;
    private static char DISPLAY_CHAR = '{';
    /**
     * Constructor.
     *
     * @param name        the name of the Actor
     */
    public Bird(String name)
    {
        super(name, DISPLAY_CHAR, HITPOINTS);
        addSkill(PassableTerrain.WATER);
        addSkill(PassableTerrain.LAND);
    }

    @Override
    public Boolean isHealthy()
    {
        return hitPoints == HITPOINTS;
    }

    @Override
    public Boolean isFlying()
    {
        return true;
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
}
