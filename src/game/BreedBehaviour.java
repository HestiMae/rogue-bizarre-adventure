package game;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.GameMap;
import edu.monash.fit2099.engine.Location;

import java.util.Random;

public class BreedBehaviour extends Action implements Behaviour
{
    private Dinosaur parent;

    public BreedBehaviour(Dinosaur parent)
    {
        this.parent = parent;
    }

    @Override
    public Action getAction(Actor actor, GameMap map)
    {
        Random rand = new Random();
        if (map.contains(actor) && rand.nextFloat() < 0.05f
                && parent.canBreed())
        {
            return this;
        }
        return null;
    }

    @Override
    public String execute(Actor actor, GameMap map)
    {
        Location here = map.locationOf(parent);
        here.addItem(new Egg(parent));
        return parent.dinoType() + " has made an egg at (" + here.x() + ", " + here.y() + ")";
    }

    @Override
    public String menuDescription(Actor actor)
    {
        return parent.dinoType() + " breeds.";
    }
}
