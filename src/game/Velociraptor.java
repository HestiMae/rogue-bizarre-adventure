package game;

import edu.monash.fit2099.engine.*;

public class Velociraptor extends Dinosaur
{
    private static final int HIT_POINTS = 75;
    private static final char DISPLAY_CHAR = 'r';
    private static final int MAX_HUNGER = 100;
    private static final int HATCH_TIME = 15;
    private static final int BREED_HUNGER = 50;



    public Velociraptor(String name)
    {
        super(name, DISPLAY_CHAR, HIT_POINTS);
        diet.add(FoodType.MEAT);
        behaviours.add(new WanderBehaviour());
        behaviours.add(new EatBehaviour(this, diet));
        behaviours.add(new HuntBehaviour(this));
    }

    /**
     * Figure out what to do next.
     * <p>
     * FIXME: Velociraptor wanders around at random, or if no suitable MoveActions are available, it
     * just stands there.  That's boring.
     *
     * @see edu.monash.fit2099.engine.Actor#playTurn(Actions, Action, GameMap, Display)
     */
    @Override
    public Action playTurn(Actions actions, Action lastAction, GameMap map, Display display)
    {
        Action wander = behaviours.get(0).getAction(this, map);
        if (wander != null)
            return wander;

        return new DoNothingAction();
    }

    @Override
    public String dinoType()
    {
        return "Velociraptor";
    }

    @Override
    public int getMaxHunger()
    {
        return MAX_HUNGER;
    }

    @Override
    public int getHatchTime()
    {
        return HATCH_TIME;
    }

    @Override
    public boolean canBreed()
    {
        return this.hungerLevel > BREED_HUNGER && this.stage == DinoAge.ADULT;
    }

    @Override
    public boolean isFull()
    {
        return this.hungerLevel == MAX_HUNGER - 20;
    }

    @Override
    public int getValue()
    {
        return 2000;
    }

    @Override
    public int getFoodValue()
    {
        return 10;
    }

    @Override
    public FoodType getFoodType()
    {
        return FoodType.MEAT;
    }
}
