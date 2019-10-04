package game;

import edu.monash.fit2099.engine.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Behaviour allowing Dinosaurs to search for suitable food in the map.
 */
public class EatBehaviour extends Action implements Behaviour
{
    private Dinosaur dino; //the Dinosaur doing the eating
    private List<FoodType> diet; //the diet of the Dinosaur

    /**
     * Constructor
     * @param dino the Dinosaur doing the eating
     * @param diet the diet of the Dinosaur
     */
    public EatBehaviour(Dinosaur dino, List<FoodType> diet)
    {
        this.dino = dino;
        this.diet = diet;
    }

    @Override
    public Action getAction(Actor actor, GameMap map)
    {
        Location startPoint = map.locationOf(dino);
        Set<Location> visitedLocations = new HashSet<>();
        Set<Location> locationsToVisit = new HashSet<>();
        locationsToVisit.add(startPoint);

        if (!dino.isFull())
        {
            if (hasEdible(startPoint)) //checks if there is an edible at the Dinosaurs location that is in their diet
            {
                return this;
            }


            while (!visitedLocations.containsAll(locationsToVisit)) //loops until all locations to check have been checked
            {
                for (Location location : new HashSet<>(locationsToVisit))
                {
                    for (Exit exit : location.getExits())
                    {
                        locationsToVisit.add(exit.getDestination()); //adds each exit from the locations to check to the list of locations to check
                    }
                    if (hasEdible(location) && !(map.isAnActorAt(location))) //checks if the current location has an edible, and it isn't currently occupied.
                    {
                        return new ApproachAction(location); //moves the dinosaur towards the edible
                    }
                    visitedLocations.add(location); //adds the location to the list of checked locations
                }
                locationsToVisit.removeAll(visitedLocations); //removes locations already checked/"visited"
            }
        }
        return null;
    }

    @Override
    public String execute(Actor actor, GameMap map)
    {
        Location here = map.locationOf(dino);
        if (here.getGround() instanceof Edible) //if the ground is an edible, do this
        {
            Ground ground = here.getGround();
            dino.eat(((Edible) here.getGround()));
            here.setGround(new Dirt()); //ground is set to dirt once eaten
            return dino + " eats " + ground.getName() + " at (" + here.x() + ", " + here.y() + ")"
                    + " Dino hunger: " + dino.hungerLevel;
        } else if (here.getItems().stream().anyMatch(x -> x instanceof Edible)) //or if there is an item that is edible, do this
        {
            for (Item item : here.getItems())
            {
                if (item instanceof Edible) //finds the edible item from the location's list
                {
                    dino.eat(((Edible) item));
                    String name = item.toString();
                    here.removeItem(item);
                    return dino + " eats " + name + " at (" + here.x() + ", " + here.y() + ")"
                            + " Dino hunger: " + dino.hungerLevel;
                }
            }
        }
        return null;
    }

    /**
     * Checks if a location contains an edible. Finds both Ground and Items
     * @param location the location to check
     * @return true if an edible is found, false if not.
     */
    private boolean hasEdible(Location location)
    {
        if (location.getGround() instanceof Edible && diet.contains(((Edible) location.getGround()).getFoodType())||
                location.getItems().stream().anyMatch(x -> x instanceof Edible &&
                        diet.contains(((Edible) x).getFoodType()))) //checks if the ground is edible and the food type is in the diet, or if an item is edible and is in the diet.
        {
            return true;
        } else
        {
            return false;
        }
    }

    @Override
    public String menuDescription(Actor actor)
    {
        return null;
    }
}
