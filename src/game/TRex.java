package game;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actions;
import edu.monash.fit2099.engine.Display;
import edu.monash.fit2099.engine.GameMap;

import java.util.Collections;
import java.util.List;

public class TRex extends Dinosaur
{
    private static final int HIT_POINTS = 500; //max HP for tyrannosaurus rex
    private static final char DISPLAY_CHAR = 'T'; //display character for tyrannosaurus rex
    private static final int MAX_HUNGER = 200; //the max metabolise level for tyrannosaurus rex
    private static final int HUNGRY_AT_LEVEL = MAX_HUNGER - 30;
    private static final int HATCH_TIME = 20; //the time a tyrannosaurus rex egg takes to hatch
    private static final int BREED_HUNGER = 100; //the metabolise level tyrannosaurus rex require to breed
    private static final int ADULT_AGE = 30; //the age in turns baby tyrannosaurus rex will become adults
    private static final int START_HUNGER_LEVEL = 80; //the metabolise level of a new tyrannosaurus rex
    private static final int HUNGER_THRESHOLD = 80; //the metabolise level when the tyrannosaurus rex is considered "hungry" - players are warned once it reaches this level
    private static final int HUNGER_LOSS = 1; //the metabolise level loss per turn
    private static final int HUNGER_DAMAGE = 30; //the HP damage per turn the metabolise level is at 0
    private static final int COST = 200000; //The monetary value of a tyrannosaurus rex
    private static final int FOOD_VALUE = 100; //the food value of a tyrannosaurus rex
    private static final int EGG_COST = 100000; //the cost of a tyrannosaurus rex egg
    private static final List<PassableTerrain> EGG_TERRAIN = Collections.singletonList(PassableTerrain.LAND); //terrain type the tyrannosaurus rex can lay eggs on
    private static int MOVE_SPEED = 1;



    /**
     * Constructor.
     * @param name the name of the Trex
     */
    public TRex(String name)
    {
        super(name, DISPLAY_CHAR, HIT_POINTS, EGG_COST, MOVE_SPEED);
        diet.add(FoodType.MEAT);
        this.foodLevel = START_HUNGER_LEVEL;
        addSkill(PassableTerrain.LAND);
        behaviours.add(new EatBehaviour(this, diet));
        behaviours.add(new HuntBehaviour(this));
        behaviours.add(new FollowBehaviour<Dinosaur>(this, Dinosaur::isHungry, (x, y) -> false, Dinosaur::canHunt, Dinosaur::canEat));
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
        return new TRex(this.name);
    }

    @Override
    Dinosaur copyDinosaur(String nameExtension)
    {
        return new TRex(this.name + nameExtension);
    }

    @Override
    public String dinoType()
    {
        return "Tyrannosaurus Rex";
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
