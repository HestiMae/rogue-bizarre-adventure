package game;

import edu.monash.fit2099.engine.*;

import java.util.ArrayList;
import java.util.List;

public abstract class Dinosaur extends Actor implements Sellable, Edible
{
    protected int age;
    protected List<Behaviour> behaviours;
    protected DinoAge stage;
    protected int hungerLevel;
    protected List<FoodType> diet;

    public Dinosaur(String name, char displayChar, int hitPoints)
    {
        super(name, displayChar, hitPoints);
        this.age = 0;
        this.stage = DinoAge.BABY;
        this.behaviours = new ArrayList<>();
        this.diet = new ArrayList<>();
        behaviours.add(new BreedBehaviour(this));
    }

    abstract Dinosaur copyDinosaur();


    @Override
    public Actions getAllowableActions(Actor otherActor, String direction, GameMap map)
    {
        Actions actions = new Actions();
        actions.add(new AttackAction(this));
        actions.add(new TagAction(this));
        List<Item> items = otherActor.getInventory();
        for (Item item: items)
        {
            // Only Edible food types which are in the dinosaurs diet can be fed to them
            if ((item instanceof Edible) && (this.diet.contains(((Edible) item).getFoodType())))
            {
                actions.add(new FeedAction(this, (Edible) item));
            }
        }

        return actions;
    }

    @Override
    public Action playTurn(Actions actions, Action lastAction, GameMap map, Display display)
    {
        if (isConscious())
        {
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
            map.locationOf(this).addItem(new Corpse("corpse", this));
            map.removeActor(this);
        }
        return new DoNothingAction();
    }

    public abstract String dinoType();

    protected void age(int adultAge)
    {
        this.age++;
        if (age == adultAge)
        {
            stage = DinoAge.ADULT;
        }
    }

    protected void hunger(int hungerLoss)
    {
        if (this.hungerLevel > 0)
        {
            this.hungerLevel -= hungerLoss;
        }
    }

    String isHungry(int hungryTime)
    {
        return this.dinoType() + " is " + ((hungerLevel > hungryTime)? " well fed!" : " getting hungry, current hunger level " + hungerLevel);
    }

    public void eat(Edible food)
    {
        if (food.getFoodValue() + hungerLevel <= getMaxHunger())
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
        if (this.hitPoints < this.maxHitPoints)
        {
            return false;
        }
        return true;
    }


    public abstract int getMaxHunger();

    public abstract int getHatchTime();

    public abstract boolean canBreed();

    public abstract boolean isFull();

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

    public DinoAge dinoStage()
    {
        return stage;
    }

}
