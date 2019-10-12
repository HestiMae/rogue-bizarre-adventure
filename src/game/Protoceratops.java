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
    private static final int MAX_HUNGER = 50; //the max metabolise level for protoceratops
    private static final int HUNGRY_AT_LEVEL = MAX_HUNGER - 10;
    private static final int HATCH_TIME = 10; //the time a protoceratops egg takes to hatch
    private static final int BREED_HUNGER = 40; //the metabolise level protoceratops require to breed
    private static final int START_HUNGER_LEVEL = 30; //the metabolise level of a new protoceratops
    private static final int HUNGER_THRESHOLD = 20; //the metabolise level when the dinosaur is considered "hungry" - players are warned once it reaches this level
    private static final int HUNGER_LOSS = 2; //the metabolise level loss per turn
    private static final int HUNGER_DAMAGE = 10; //the HP damage per turn the metabolise level is at 0
    private static final int COST = 100; //The monetary value of a protoceratops
    private static final int FOOD_VALUE = 30; //the food value of a protoceratops
    private static final int EGG_COST = 10; //the cost of a protoceratops egg
    private static final PassableTerrain EGG_TERRAIN = PassableTerrain.LAND; //terrain type the protoceratops can lay eggs on


    /**
     * Constructor.
     * @param name the name of the dinosaur
     */
    public Protoceratops(String name)
    {
        super(name, DISPLAY_CHAR, HIT_POINTS, EGG_COST);
        diet.add(FoodType.PLANT);
        this.foodLevel = START_HUNGER_LEVEL;
        addSkill(PassableTerrain.LAND);
        behaviours.add(new EatBehaviour(this, diet));
        behaviours.add(new FollowBehaviour<Dinosaur>(this, Dinosaur::isHungry,
                Dinosaur::canEat,
                (dinosaur, actor) -> false,
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

    public static int getBreedHunger()
    {
        return BREED_HUNGER;
    }

    @Override
    Dinosaur copyDinosaur()
    {
        return new Protoceratops(this.name);
    }

    @Override
    Dinosaur copyDinosaur(String nameExtension)
    {
        return new Protoceratops(this.name + nameExtension);
    }

    @Override
    public String dinoType()
    {
        return "Protoceratops";
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
    public Boolean isFlying()
    {
        return false;
    }
}
