package game;

import edu.monash.fit2099.engine.*;

public class Velociraptor extends Dinosaur
{
    private static final int HIT_POINTS = 75;
    private static final char DISPLAY_CHAR = 'r';
    private static final int MAX_HUNGER = 100;
    private static final int HATCH_TIME = 15;
    private static final int BREED_HUNGER = 50;
    private static final int ADULT_AGE = 20;
    private static final int START_HUNGER_LEVEL = 30;
    private static final int HUNGER_THRESHOLD = 20;
    public static final int HUNGER_LOSS = 1;
    public static final int HUNGER_DAMAGE = 10;


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
    } //TODO: Put all the implementations for this stuff thats the same in the superclass.

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
