package game;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actions;
import edu.monash.fit2099.engine.Display;
import edu.monash.fit2099.engine.GameMap;

public class Pteranodon extends Dinosaur
{
    private static final int HIT_POINTS = 75; //max HP for pteranodons
    private static final char DISPLAY_CHAR = 'W'; //display character for pteranodons
    private static final int ADULT_AGE = 20; //the age in turns baby pteranodons will become adults
    private static final int MAX_HUNGER = 50; //the max hunger level for pteranodons
    private static final int HUNGRY_AT_LEVEL = MAX_HUNGER - 10;
    private static final int HATCH_TIME = 10; //the time a pteranodon egg takes to hatch
    private static final int BREED_HUNGER = 40; //the hunger level pteranodon require to breed
    private static final int START_HUNGER_LEVEL = 30; //the hunger level of a new pteranodon
    private static final int HUNGER_THRESHOLD = 20; //the hunger level when the dinosaur is considered "hungry" - players are warned once it reaches this level
    private static final int HUNGER_LOSS = 2; //the hunger level loss per turn
    private static final int HUNGER_DAMAGE = 10; //the HP damage per turn the metabolise level is at 0
    private static final int COST = 20000; //The monetary value of a pteranodon
    private static final int FOOD_VALUE = 30; //the food value of a pteranodon
    private static final PassableTerrain EGG_TERRAIN = PassableTerrain.LAND; //the type of land pteranodons lay eggs on
    private static final int EGG_COST = 10000; //the cost of a pteranodons egg

    /**
     * Constructor. All new Dinosaurs are considered babies, with their age being 0.
     *
     * @param name the name of the dinosaur

     */
    public Pteranodon(String name)
    {
        super(name, DISPLAY_CHAR, HIT_POINTS, EGG_COST);
        diet.add(FoodType.MARINE);
        diet.add(FoodType.MEAT);
        this.foodLevel = START_HUNGER_LEVEL;
        addSkill(PassableTerrain.LAND);
        addSkill(PassableTerrain.WATER);
        behaviours.add(new EatBehaviour(this, diet));
        behaviours.add(new FollowBehaviour<Dinosaur>(this, Dinosaur::isHungry,
                Dinosaur::canEat,
                Dinosaur::canHunt,
                Dinosaur::canEat));
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
        return new Pteranodon(this.name);
    }

    @Override
    Dinosaur copyDinosaur(String nameExtension)
    {
        return new Pteranodon(nameExtension);
    }

    @Override
    public String dinoType()
    {
        return "Pteranodon";
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
        return this.foodLevel < HUNGRY_AT_LEVEL;
    }

    @Override
    public PassableTerrain getEggTerrain() { return EGG_TERRAIN; }

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

    @Override
    public int getValue()
    {
        return COST;
    }
}
