package game;

import edu.monash.fit2099.engine.*;

import java.util.ArrayList;
import java.util.List;

public abstract class Dinosaur extends Actor
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


    @Override
    public Actions getAllowableActions(Actor otherActor, String direction, GameMap map)
    {
        return new Actions(new AttackAction(this));
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
    protected void hungry(int hungerLoss)
    {
        this.hungerLevel -= hungerLoss;
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
    public abstract int getMaxHunger();

    public abstract int getHatchTime();

    public abstract boolean canBreed();

    public abstract boolean isFull();

    public DinoAge dinoStage()
    {
        return stage;
    }

}