package game;

import edu.monash.fit2099.engine.*;

import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class RangedBehaviour implements Behaviour
{
    List<SellableWeapon> weapons;
    @Override
    public Action getAction(Actor actor, GameMap map)
    {
        return getAllActions(actor, map).getUnmodifiableActionList().stream().findFirst().orElse(null);
    }

    @Override
    public Actions getAllActions(Actor actor, GameMap map)
    {
        Actions actions = new Actions();
        Location startPoint = map.locationOf(actor);
        Set<Location> visitedLocations = new HashSet<>();
        Set<Location> locationsToVisit = new HashSet<>();
        locationsToVisit.add(startPoint);
        int maxRange = actor.getInventory().stream()
                .filter(item -> item instanceof SellableWeapon && ((SellableWeapon) item).getRange() > 1)
                .map(item -> ((SellableWeapon) item).getRange()).reduce(Integer::max).orElse(-1);
        int counter = 0;

        while (!visitedLocations.containsAll(locationsToVisit) && counter < maxRange) //loops until all locations to check have been checked
        {
            for (Location location : new HashSet<>(locationsToVisit))
            {
                for (Exit exit : location.getExits())
                {
                    locationsToVisit.add(exit.getDestination()); //adds each exit from the locations to check to the list of locations to check
                }
                if (hasTarget(location, actor)) //checks if the current location has a target object
                {
                    int finalCounter = counter;
                    actor.getInventory().stream()
                            .filter(item -> item instanceof SellableWeapon)
                            .filter(item -> ((SellableWeapon) item).getRange() >= finalCounter)
                            .forEach(item -> actions.add(new AttackAction(location.getActor(), (Weapon) item)));
                }
                visitedLocations.add(location); //adds the location to the list of checked locations
            }
            locationsToVisit.removeAll(visitedLocations); //removes locations already checked/"visited"
            counter++;
        }
        return actions;
    }

    boolean hasTarget(Location location, Actor actor)
    {
        return location.containsAnActor() && !location.getActor().getClass().isAssignableFrom(actor.getClass());
    }
}
