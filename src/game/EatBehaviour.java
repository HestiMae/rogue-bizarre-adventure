package game;

import edu.monash.fit2099.engine.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class EatBehaviour extends Action implements Behaviour
{
    private Dinosaur dino;
    private List<FoodType> diet;

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
            if (hasEdible(startPoint))
            {
                return this;
            }


            while (!visitedLocations.containsAll(locationsToVisit))
            {
                for (Location location : new HashSet<>(locationsToVisit))
                {
                    for (Exit exit : location.getExits())
                    {
                        locationsToVisit.add(exit.getDestination());
                    }
                    if (hasEdible(location))
                    {
                        return moveLogic(startPoint, location);
                    }
                    visitedLocations.add(location);
                }
                locationsToVisit.removeAll(visitedLocations);
            }
        }
        return null;
    }

    @Override
    public String execute(Actor actor, GameMap map)
    {
        Location here = map.locationOf(dino);
        if (here.getGround() instanceof Edible)
        {
            dino.eat(((Edible) here.getGround()));
            here.setGround(new Dirt());
            return dino.dinoType() + " eats ground at (" + here.x() + ", " + here.y() + ")"
                    + " Dino hunger: " + dino.hungerLevel;
        } else if (here.getItems().stream().anyMatch(x -> x instanceof Edible))
        {
            for (Item item : here.getItems())
            {
                if (item instanceof Edible)
                {
                    dino.eat(((Edible) item));
                    String name = item.toString();
                    here.removeItem(item);
                    return dino.dinoType() + " eats " + name + " at (" + here.x() + ", " + here.y() + ")"
                            + " Dino hunger: " + dino.hungerLevel;
                }
            }
        }
        return null;
    }

    private boolean hasEdible(Location location)
    {
        if (location.getGround() instanceof Edible ||
                location.getItems().stream().anyMatch(x -> x instanceof Edible &&
                        diet.contains(((Edible) x).getFoodType())))
        {
            return true;
        } else
        {
            return false;
        }
    }

    private Action moveLogic(Location here, Location edibleLocation)
    {

        return Util.moveLogic(here, edibleLocation, dino);
    }

    @Override
    public String menuDescription(Actor actor)
    {
        return null;
    }
}
