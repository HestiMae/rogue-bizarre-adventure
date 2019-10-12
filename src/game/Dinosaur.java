package game;

import edu.monash.fit2099.engine.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Dinosaur abstract Class
 */
public abstract class Dinosaur extends Actor implements Sellable, Edible
{
    private static final int HUNGER_HEAL = 20; //the amount of health to heal a dinosaur when its metabolise is full
    private int age; //the age of the Dinosaur in turns
    List<Behaviour> behaviours; //a list of possible behaviours
    DinoAge stage; //the stage of life the Dinosaur is at.
    int foodLevel; //the current food level
    List<FoodType> diet; //the diet of the Dinosaur. Having a List allows for Omnivores
    private int lastTurnFoodLevel; //the Hunger on the previous turn. Used for output purposes.
    private int eggValue; //the cost of an egg for each dinosaur type

    /**
     * Constructor. All new Dinosaurs are considered babies, with their age being 0.
     * @param name        the name of the dinosaur
     * @param displayChar the display character for the dinosaur
     * @param hitPoints   the hitPoints of the Dinosaur
     * @param eggValue the cost of this dinosaurs eggs
     */
    public Dinosaur(String name, char displayChar, int hitPoints, int eggValue)
    {
        super(name, displayChar, hitPoints);
        this.age = 0;
        this.stage = DinoAge.BABY;
        this.eggValue = eggValue;
        this.behaviours = new ArrayList<>();
        this.diet = new ArrayList<>();
        behaviours.add(new BreedBehaviour(this));
    }

    /**
     * Simple "Copy constructor"
     *
     * @return a copy of the Dinosaur
     */
    abstract Dinosaur copyDinosaur();

    abstract Dinosaur copyDinosaur(String nameExtension);


    @Override
    public Actions getAllowableActions(Actor otherActor, String direction, GameMap map)
    {
        Actions actions = new Actions();
        actions.add(new AttackAction(this)); //Dinosaurs can be attacked when nearby
        actions.add(new TagAction(this)); //Dinosaurs can be tagged when nearby
        List<Item> items = otherActor.getInventory();
        for (Item item : items)
        {
            // Only Edible food types which are in the dinosaurs diet can be fed to them
            if ((item instanceof Edible) && (this.diet.contains(((Edible) item).getFoodType())))
            {
                actions.add(new FeedAction(this, (Edible) item)); //adds a FeedAction for every Edible item that is in the Dinosaurs diet.
            }
        }

        return actions;
    }

    @Override
    public Action playTurn(Actions actions, Action lastAction, GameMap map, Display display)
    {
        if (isConscious()) //checks if the Dinosaur is Conscious
        {
            displayHungerHeal(display);
            for (Behaviour b : behaviours)
            {
                Action outAction = b.getAction(this, map);
                if (outAction != null)
                {
                    return outAction;
                }
            }
        } else
        {
            map.locationOf(this).addItem(new Corpse("corpse", this)); //Adds a corpse to the Dinosaurs location if it isn't conscious
            map.removeActor(this); //removes the dinosaur after adding the corpse
        }
        return new DoNothingAction();
    }

    /**
     * Returns a String for the type of dinosaur e.g. "Protoceratops" for Protoceratops.
     *
     * @return the type of Dinosaur
     */
    public abstract String dinoType();

    /**
     * Ages the dinosaur every turn. Changes the stage of life once it reaches adulthood
     *
     * @param adultAge the age the dinosaur reaches adulthood.
     */
    void age(int adultAge)
    {
        this.age++;
        if (age == adultAge)
        {
            stage = DinoAge.ADULT;
        }
    }

    /**
     * Decrements metabolise level every turn by a specifiable amount
     *
     * @param hungerLoss the amount of foodlevel to lose per turn
     */
    void metabolise(int hungerLoss)
    {
        lastTurnFoodLevel = this.foodLevel;
        if (this.foodLevel > 0)
        {
            this.foodLevel -= hungerLoss;
        }
    }

    /**
     * Prints a string descriptor to alert the player if a Dinosaur is hungry
     *
     * @param hungryTime the metabolise level to start warning the player at
     */
    void hungerStatus(int hungryTime, Display display)
    {
        if (foodLevel < hungryTime && lastTurnFoodLevel < hungryTime) //TODO: Only outputs if dino is continuously hungry. Maybe change to be when dino is only just hungry?
        {
            display.println(this + " is getting hungry, current hunger level " + foodLevel);
        }
    }

    /**
     * Eats an edible
     *
     * @param food the edible to be eaten
     */
    public void eat(Edible food)
    {
        if (food.getFoodValue() + foodLevel <= getMaxFoodLevel()) //ensures the food level can't go over the maximum.
        {
            this.foodLevel += food.getFoodValue();
        } else
        {
            this.foodLevel = getMaxFoodLevel();
        }
    }

    @Override
    public Boolean isHealthy()
    {
        return this.hitPoints >= this.maxHitPoints;
    }


    /**
     * Gets the maximum foodlevel for a Dinosaur
     *
     * @return the maximum metabolise level
     */
    public abstract int getMaxFoodLevel();

    /**
     * Gets the amount of time a Dinosaur species takes to hatch
     *
     * @return hatchtime
     */
    public abstract int getHatchTime();

    /**
     * Checks whether a Dinosaur is in a state to breed or not
     *
     * @return true if it can breed, false if not
     */
    public abstract boolean canBreed();

    /**
     * Checks if the dinosaur is full or not
     *
     * @return true if it is full, false if not
     */
    public abstract boolean isHungry();

    /**
     * Gets the type of terrain that the dinosaur lays its eggs on
     * @return enum for terrain type
     */
    public abstract PassableTerrain getEggTerrain();

    /**
     * Hurts the dinosaur for every turn it is at 0 foodlevel.
     *
     * @param hungerDamage the damage to deal per turn
     */
    public void starve(int hungerDamage)
    {
        if (this.foodLevel == 0)
        {
            if (isConscious())
            {
                hurt(hungerDamage);
            }
        }
    }

    private void displayHungerHeal(Display display)
    {
        if (foodLevel == getMaxFoodLevel() && !isHealthy())
        {
            heal(HUNGER_HEAL);
            display.println("Your " + this + " is full! They've been healed for " + HUNGER_HEAL + " HP");
        }
    }

    /**
     * Gets the life stage of the Dinosaur
     *
     * @return the life stage of the Dinosaur
     */
    public DinoAge dinoStage()
    {
        return stage;
    }

    public List<FoodType> getDiet()
    {
        return new ArrayList<>(diet);
    }

    @Override
    public int getHP()
    {
        return hitPoints;
    }

    protected boolean canEat(Object possibleEdible)
    {
        return possibleEdible instanceof Edible && this.diet.contains(((Edible) possibleEdible).getFoodType());
    }

    protected boolean canHunt(Actor possiblePrey)
    {
        boolean flyingCheck = true;
        if (!this.isFlying() && possiblePrey.isFlying())
        {
            flyingCheck = false;
        }
        return possiblePrey != null && !this.getClass().isAssignableFrom(possiblePrey.getClass())
                && canEat(possiblePrey) && flyingCheck;
    }

    public int getEggValue()
    {
        return eggValue;
    }
}
