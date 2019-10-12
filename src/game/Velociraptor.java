package game;

import edu.monash.fit2099.engine.*;

public class Velociraptor extends Dinosaur
{
    private static final int HIT_POINTS = 75; //max HP for velociraptor
    private static final char DISPLAY_CHAR = 'r'; //display character for velociraptor
    private static final int MAX_HUNGER = 100; //the max metabolise level for velociraptor
    private static final int HUNGRY_AT_LEVEL = MAX_HUNGER - 20;
    private static final int HATCH_TIME = 15; //the time a velociraptor egg takes to hatch
    private static final int BREED_HUNGER = 50; //the metabolise level velociraptor require to breed
    private static final int ADULT_AGE = 20; //the age in turns baby velociraptor will become adults
    private static final int START_HUNGER_LEVEL = 30; //the metabolise level of a new velociraptor
    private static final int HUNGER_THRESHOLD = 20; //the metabolise level when the velociraptor is considered "hungry" - players are warned once it reaches this level
    private static final int HUNGER_LOSS = 1; //the metabolise level loss per turn
    private static final int HUNGER_DAMAGE = 10; //the HP damage per turn the metabolise level is at 0
    private static final int COST = 2000; //The monetary value of a velociraptor
    private static final int FOOD_VALUE = 10; //the food value of a velociraptor
    private static final PassableTerrain EGG_TERRAIN = PassableTerrain.LAND; //terrain type the velociraptor can travel through


    /**
     * Constructor.
     * @param name the name of the velociraptor
     */
    public Velociraptor(String name)
    {
        super(name, DISPLAY_CHAR, HIT_POINTS);
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
        display.println(hungerStatus(HUNGER_THRESHOLD));
        return super.playTurn(actions, lastAction, map, display);
    }

    @Override
    Dinosaur copyDinosaur()
    {
        return new Velociraptor(this.name);
    }

    @Override
    Dinosaur copyDinosaur(String nameExtension)
    {
        return new Velociraptor(this.name + nameExtension);
    }

    @Override
    public String dinoType()
    {
        return "Velociraptor";
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
    public Boolean isFlying()
    {
        return false;
    }

    @Override
    public PassableTerrain getEggTerrain() { return EGG_TERRAIN; }
}
