package game;

import edu.monash.fit2099.engine.*;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Function;

/**
 * Just a place to put useful calculations and such that will get used in different places.
 */
public class Util
{

    /**
     * Distance calculation between 2 locations, allowing for diagonal movement.
     * @param a first location
     * @param b second location
     * @return the distance
     */
    public static int distance(Location a, Location b)
    {
        return Math.max(Math.abs(a.x()-b.x()), Math.abs(a.y()-b.y())); //Since we can move diagonally, the distance is just the greater of x or y.
    }

    /**
     * Returns the coords of a location in a useful format
     * @param location the location to get the coords of
     * @return a String of the coords of the location
     */
    public static String locationString(Location location)
    {
        return "(" + location.x() + ", " + location.y() + ")";
    }

    public static Action searchAlgorithm(Actor actor, GameMap map, Function<Location, Boolean> hasTarget, int range)
    {
        Location startPoint = map.locationOf(actor);
        Set<Location> visitedLocations = new HashSet<>();
        Set<Location> locationsToVisit = new HashSet<>();
        locationsToVisit.add(startPoint);
        int counter = 0;

        while (!visitedLocations.containsAll(locationsToVisit) && counter < range) //loops until all locations to check have been checked
        {
            for (Location location : new HashSet<>(locationsToVisit))
            {
                for (Exit exit : location.getExits())
                {
                    locationsToVisit.add(exit.getDestination()); //adds each exit from the locations to check to the list of locations to check
                }
                if (hasTarget.apply(location)) //checks if the current location has a target object
                {
                    return new ApproachAction(location); //moves the actor towards the object
                }
                visitedLocations.add(location); //adds the location to the list of checked locations
            }
            locationsToVisit.removeAll(visitedLocations); //removes locations already checked/"visited"
            counter++;
        }
        return null;
    }
}