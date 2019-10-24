package game;

public class Fish extends SimpleCreature
{
    private static int HITPOINTS = 10;
    private static int FOOD_VALUE = 10;
    private static char DISPLAY_CHAR = '^';
    private static boolean MOVE_TWO = false;

    /**
     * Constructor.
     *
     * @param name        the name of the Actor
     */
    public Fish(String name)
    {
        super(name, DISPLAY_CHAR, HITPOINTS, MOVE_TWO);
        addSkill(PassableTerrain.WATER);
    }

    @Override
    public Boolean isHealthy()
    {
        return hitPoints == HITPOINTS;
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
