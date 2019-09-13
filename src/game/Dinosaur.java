package game;

import edu.monash.fit2099.engine.Actions;
import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.GameMap;

public abstract class Dinosaur extends Actor
{
    private int age;


    public Dinosaur(String name, char displayChar, int hitPoints)
    {
        super(name, displayChar, hitPoints);
    }

    @Override
    public Actions getAllowableActions(Actor otherActor, String direction, GameMap map)
    {
        return new Actions(new AttackAction(this));
    }
}
