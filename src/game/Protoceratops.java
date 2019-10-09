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
    private static final int HIT_POINTS = 100; //max HP for protoceratops
    private static final char DISPLAY_CHAR = 'd'; //display character for protoceratops
    private static final int ADULT_AGE = 20; //the age in turns baby protoceratops will become adults
    private static final int MAX_HUNGER = 50; //the max hunger level for protoceratops
    private static final int HATCH_TIME = 10; //the time a protoceratops egg takes to hatch

    public static int getBreedHunger()
    {
        return BREED_HUNGER;
    }

    private static final int BREED_HUNGER = 40; //the hunger level protoceratops require to breed
    private static final int START_HUNGER_LEVEL = 50; //the hunger level of a new protoceratops
    private static final int HUNGER_THRESHOLD = 20; //the hunger level when the dinosaur is considered "hungry" - players are warned once it reaches this level
    private static final int HUNGER_LOSS = 2; //the hunger level loss per turn
    private static final int HUNGER_DAMAGE = 10; //the HP damage per turn the hunger level is at 0
    private static final int COST = 100; //The monetary value of a protoceratops
    private static final int FOOD_VALUE = 30; //the food value of a protoceratops


    /**
     * Constructor.
     * @param name the name of the dinosaur
     */
    public Protoceratops(String name)
    {
        super(name, DISPLAY_CHAR, HIT_POINTS);
        diet.add(FoodType.PLANT);
        this.hungerLevel = START_HUNGER_LEVEL;
        behaviours.add(new EatBehaviour(this, diet));
        behaviours.add(new WanderBehaviour());
    }

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
        return new Protoceratops(this.name + " Jr.");
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
        return COST;
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
