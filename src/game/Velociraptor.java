package game;

import edu.monash.fit2099.engine.*;

public class Velociraptor extends Dinosaur
{
    private static final int HIT_POINTS = 75; //max HP for velociraptor
    private static final char DISPLAY_CHAR = 'r'; //display character for velociraptor
    private static final int MAX_HUNGER = 100; //the max hunger level for velociraptor
    private static final int HATCH_TIME = 15; //the time a velociraptor egg takes to hatch
    private static final int BREED_HUNGER = 50; //the hunger level velociraptor require to breed
    private static final int ADULT_AGE = 20; //the age in turns baby velociraptor will become adults
    private static final int START_HUNGER_LEVEL = 30; //the hunger level of a new velociraptor
    private static final int HUNGER_THRESHOLD = 20; //the hunger level when the velociraptor is considered "hungry" - players are warned once it reaches this level
    public static final int HUNGER_LOSS = 1; //the hunger level loss per turn
    public static final int HUNGER_DAMAGE = 10; //the HP damage per turn the hunger level is at 0
    public static final int COST = 2000; //The monetary value of a velociraptor
    public static final int FOOD_VALUE = 10; //the food value of a velociraptor


    /**
     * Constructor.
     * @param name the name of the velociraptor
     */
    public Velociraptor(String name)
    {
        super(name, DISPLAY_CHAR, HIT_POINTS);
        diet.add(FoodType.MEAT);
        this.hungerLevel = START_HUNGER_LEVEL;
        behaviours.add(new EatBehaviour(this, diet));
        behaviours.add(new HuntBehaviour(this));
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
        return new Velociraptor(this.name);
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
