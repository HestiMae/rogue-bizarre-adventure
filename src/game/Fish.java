package game;

public class Fish extends SimpleCreature
{
    private static int HITPOINTS = 10;
    private static int FOOD_VALUE = 10;
    private static char DISPLAY_CHAR = '^';
    /**
     * Constructor.
     *
     * @param name        the name of the Actor
     */
    public Fish(String name)
    {
        super(name, DISPLAY_CHAR, HITPOINTS);
        addSkill(PassableTerrain.WATER);
    }

    @Override
    public Boolean isHealthy()
    {
        return hitPoints == HITPOINTS;
    }

    @Override
    public Boolean isFlying()
    {
        return false;
    }

    @Override
    public int getFoodValue()
    {
        return FOOD_VALUE;
    }

    @Override
    public FoodType getFoodType()
    {
        return FoodType.MARINE;
    }
}
