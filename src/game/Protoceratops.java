package game;


import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actions;
import edu.monash.fit2099.engine.Display;
import edu.monash.fit2099.engine.GameMap;

/**
 * A herbivorous dinosaur.
 */
public class Protoceratops extends Dinosaur
{
    private static final int HIT_POINTS = 100;
    private static final char DISPLAY_CHAR = 'd';
    private static final int ADULT_AGE = 20;
    private static final int MAX_HUNGER = 50;
    private static final int HATCH_TIME = 10;
    private static final int BREED_HUNGER = 50;
    private static final int START_HUNGER_LEVEL = 30;
    private static final int HUNGER_THRESHOLD = 20;
    public static final int HUNGER_LOSS = 2;
    public static final int HUNGER_DAMAGE = 10;


    public Protoceratops(String name)
    {
        super(name, DISPLAY_CHAR, HIT_POINTS);
        diet.add(FoodType.PLANT);
        this.hungerLevel = START_HUNGER_LEVEL;
        behaviours.add(new EatBehaviour(this, diet));
        behaviours.add(new WanderBehaviour());
    }

    /**
     * @see edu.monash.fit2099.engine.Actor#playTurn(Actions, Action, GameMap, Display)
     */
    @Override
    public Action playTurn(Actions actions, Action lastAction, GameMap map, Display display)
    {
        age(ADULT_AGE);
        hunger(HUNGER_LOSS);
        needFood(HUNGER_DAMAGE);
        display.println(isHungry(HUNGER_THRESHOLD));
        return super.playTurn(actions, lastAction, map, display);
    }

    @Override
    Dinosaur copyDinosaur()
    {
        return new Protoceratops(this.name);
    }

    @Override
    public String dinoType()
    {
        return "Protoceratops";
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
        return this.hungerLevel >= MAX_HUNGER - 10;
    }


    @Override
    public int getValue()
    {
        return 100;
    }

    @Override
    public int getFoodValue()
    {
        return 30;
    }

    @Override
    public FoodType getFoodType()
    {
        return FoodType.MEAT;
    }
}
