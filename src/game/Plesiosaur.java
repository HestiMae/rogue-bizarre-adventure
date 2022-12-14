package game;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actions;
import edu.monash.fit2099.engine.Display;
import edu.monash.fit2099.engine.GameMap;

import java.util.Collections;
import java.util.List;

public class Plesiosaur extends Dinosaur
{
    private static final int HIT_POINTS = 75; //max HP for plesiosaur
    private static final char DISPLAY_CHAR = '?'; //display character for plesiosaur
    private static final int ADULT_AGE = 20; //the age in turns baby plesiosaur will become adults
    private static final int MAX_HUNGER = 50; //the max metabolise level for plesiosaur
    private static final int HUNGRY_AT_LEVEL = MAX_HUNGER - 10;
    private static final int HATCH_TIME = 10; //the time a plesiosaur egg takes to hatch
    private static final int BREED_HUNGER = 40; //the metabolise level plesiosaur require to breed
    private static final int START_HUNGER_LEVEL = 30; //the metabolise level of a new plesiosaur
    private static final int HUNGER_THRESHOLD = 20; //the metabolise level when the dinosaur is considered "hungry" - players are warned once it reaches this level
    private static final int HUNGER_LOSS = 2; //the metabolise level loss per turn
    private static final int HUNGER_DAMAGE = 10; //the HP damage per turn the metabolise level is at 0
    private static final int COST = 20000; //The monetary value of a plesiosaur
    private static final int EGG_COST = 10000; //the cost of a plesiosaur egg
    private static final int FOOD_VALUE = 30; //the food value of a plesiosaur
    private static final List<PassableTerrain> EGG_TERRAIN = Collections.singletonList(PassableTerrain.WATER); //terrain type the plesiosaur can lay eggs on
    private static boolean MOVE_TWO = false;
    private static final int DAMAGE = 50; //the damage this dinosaur deals


    /**
     * Constructor for plesiosaur
     * @param name Name of the plesiosaur
     */
    public Plesiosaur(String name)
    {
        super(name, DISPLAY_CHAR, HIT_POINTS, EGG_COST, MOVE_TWO, DAMAGE);
        diet.add(FoodType.MARINE);
        diet.add(FoodType.MEAT);
        this.foodLevel = START_HUNGER_LEVEL;
        addSkill(PassableTerrain.WATER);
        behaviours.add(new EatBehaviour(this, diet));
        behaviours.add(new FollowBehaviour<Dinosaur>(this, Dinosaur::isHungry,
                (dinosaur, ground) -> false,
                Dinosaur::canHunt,
                (Dinosaur::canEat)));
        behaviours.add(new WanderBehaviour());
    }

    @Override
    public Action playTurn(Actions actions, Action lastAction, GameMap map, Display display)
    {
        age(ADULT_AGE);
        metabolise(HUNGER_LOSS);
        starve(HUNGER_DAMAGE);
        hungerStatus(HUNGER_THRESHOLD, display);
        return super.playTurn(actions, lastAction, map, display);
    }

    @Override
    Dinosaur copyDinosaur()
    {
        return new Plesiosaur(this.name);
    }

    @Override
    Dinosaur copyDinosaur(String nameExtension)
    {
        return new Plesiosaur(this.name + nameExtension);
    }

    @Override
    public String dinoType()
    {
        return "Plesiosaur";
    }

    @Override
    public int getMaxFoodLevel()
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
        return this.foodLevel > BREED_HUNGER && this.stage == DinoAge.ADULT;
    }

    @Override
    public boolean isHungry()
    {
        return this.foodLevel <= HUNGRY_AT_LEVEL;
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

    @Override
    public List<PassableTerrain> getEggTerrain() { return EGG_TERRAIN; }
}
