package game;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.GameMap;
import edu.monash.fit2099.engine.Location;

import java.util.Random;

/**
 * A behaviour for dinosaurs to breed. Extends Action and implements Behaviour as we can then return this.
 */
public class BreedBehaviour extends Action implements Behaviour
{
    public static final float BREED_CHANCE = 0.1f; //Chance to breed. Higher means greater chance
    private Dinosaur parent; //the parent Dinosaur who will breed

    /**
     * Simple constructor
     * @param parent the parent Dinosaur
     */
    public BreedBehaviour(Dinosaur parent)
    {
        this.parent = parent;
    }

    /**
     * Checks if the Parent is in the map, generates a random float to see if it's less than the breed chance.
     * Calls {@link Dinosaur#canBreed()} to check if the parent is in a fit state to breed.
     * @param actor the Actor acting
     * @param map the GameMap containing the Actor
     * @return BreedBehaviour Action if checks are successful, null if not.
     */
    @Override
    public Action getAction(Actor actor, GameMap map)
    {
        Random rand = new Random();
        if (map.contains(actor) && rand.nextFloat() < BREED_CHANCE
                && parent.canBreed())
        {
            return this;
        }
        return null;
    }

    /**
     * Gets the location of the parent and adds an egg there.
     * @param actor The actor performing the action.
     * @param map The map the actor is on.
     * @return a descriptive String
     */
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
