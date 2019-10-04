package game;

import edu.monash.fit2099.engine.*;

import java.util.ArrayList;
import java.util.List;

public abstract class Dinosaur extends Actor implements Sellable, Edible
{
    private static final int HUNGER_HEAL = 20; //the amount of health to heal a dinosaur when its hunger is full
    private int age; //the age of the Dinosaur in turns
    List<Behaviour> behaviours; //a list of possible behaviours
    DinoAge stage; //the stage of life the Dinosaur is at.
    int hungerLevel; //the current hunger level
    List<FoodType> diet; //the diet of the Dinosaur. Having a List allows for Omnivores

    /**
     * Constructor. All new Dinosaurs are considered babies, with their age being 0.
     * @param name the name of the dinosaur
     * @param displayChar the display character for the dinosaur
     * @param hitPoints the hitPoints of the Dinosaur
     */
    public Dinosaur(String name, char displayChar, int hitPoints)
    {
        super(name, displayChar, hitPoints);
        this.age = 0;
        this.stage = DinoAge.BABY;
        this.behaviours = new ArrayList<>();
        this.diet = new ArrayList<>();
        behaviours.add(new BreedBehaviour(this));
    }

    /**
     * Simple "Copy constructor"
     * @return a copy of the Dinosaur
     */
    abstract Dinosaur copyDinosaur();


    @Override
    public Actions getAllowableActions(Actor otherActor, String direction, GameMap map)
    {
        Actions actions = new Actions();
        actions.add(new AttackAction(this)); //Dinosaurs can be attacked when nearby
        actions.add(new TagAction(this)); //Dinosaurs can be tagged when nearby
        List<Item> items = otherActor.getInventory();
        for (Item item: items)
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
        }
        else
        {
            map.locationOf(this).addItem(new Corpse("corpse", this)); //Adds a corpse to the Dinosaurs location if it isn't conscious
            map.removeActor(this); //removes the dinosaur after adding the corpse
        }
        return new DoNothingAction();
    }

    /**
     * Returns a String for the type of dinosaur e.g. "Protoceratops" for Protoceratops.
     * @return the type of Dinosaur
     */
    public abstract String dinoType();

    /**
     * Ages the dinosaur every turn. Changes the stage of life once it reaches adulthood
     * @param adultAge the age the dinosaur reaches adulthood.
     */
    protected void age(int adultAge)
    {
        this.age++;
        if (age == adultAge)
        {
            stage = DinoAge.ADULT;
        }
    }

    /**
     * Decrements hunger level every turn by a specifiable amount
     * @param hungerLoss the amount of hunger to lose per turn
     */
    protected void hunger(int hungerLoss)
    {
        if (this.hungerLevel > 0)
        {
            this.hungerLevel -= hungerLoss;
        }
    }

    /**
     * A string descriptor to alert the player if a Dinosaur is hungry
     * @param hungryTime the hunger level to start warning the player at
     * @return a String alerting the player to the hunger level of their dinosaurs
     */
    String isHungry(int hungryTime)
    {
        return this + " is " + ((hungerLevel > hungryTime)? " well fed! Hunger level " + hungerLevel : " getting hungry, current hunger level " + hungerLevel); //uses the ternary operator to choose which String to return
    }

    /**
     * Eats an edible
     * @param food the edible to be eaten
     */
    public void eat(Edible food)
    {
        if (food.getFoodValue() + hungerLevel <= getMaxHunger()) //ensures the food level can't go over the maximum.
        {
            this.hungerLevel += food.getFoodValue();
        }
        else
        {
            this.hungerLevel = getMaxHunger();
        }
    }

    @Override
    public Boolean isHealthy()
    {
        return this.hitPoints >= this.maxHitPoints;
    }


    /**
     * Gets the maximum hunger for a Dinosaur
     * @return the maximum hunger level
     */
    public abstract int getMaxHunger();

    /**
     * Gets the amount of time a Dinosaur species takes to hatch
     * @return hatchtime
     */
    public abstract int getHatchTime();

    /**
     * Checks whether a Dinosaur is in a state to breed or not
     * @return true if it can breed, false if not
     */
    public abstract boolean canBreed();

    /**
     * Checks if the dinosaur is full or not
     * @return true if it is full, false if not
     */
    public abstract boolean isFull();

    /**
     * Hurts the dinosaur for every turn it is at 0 hunger.
     * @param hungerDamage the damage to deal per turn
     */
    public void needFood(int hungerDamage)
    {
        if (this.hungerLevel == 0)
        {
            if (isConscious())
            {
                hurt(hungerDamage);
            }
        }
    }
    private void displayHungerHeal(Display display)
    {
        if (hungerLevel == getMaxHunger() && !isHealthy())
        {
            heal(HUNGER_HEAL);
            display.println("Your " + this + " is full! They've been healed for " + HUNGER_HEAL + " HP");
        }
    }

    /**
     * Gets the life stage of the Dinosaur
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
}
