package game;

import edu.monash.fit2099.engine.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.BiFunction;
import java.util.function.BiPredicate;
import java.util.function.Function;

/**
 * Just a place to put useful calculations and such that will get used in different places.
 */
public class Util
{

    /**
     * Distance calculation between 2 locations, allowing for diagonal movement.
     *
     * @param a first location
     * @param b second location
     * @return the distance
     */
    public static int distance(Location a, Location b)
    {
        return Math.max(Math.abs(a.x() - b.x()), Math.abs(a.y() - b.y())); //Since we can move diagonally, the distance is just the greater of x or y.
    }

    /**
     * Returns the coords of a location in a useful format
     *
     * @param location the location to get the coords of
     * @return a String of the coords of the location
     */
    public static String locationString(Location location)
    {
        return "(" + location.x() + ", " + location.y() + ")";
    }

    /**
     * Search algorithm for finding locations that match a condition and doing something with them.
     * Unfortunately in generalising this often some arguments are unused - but they are required elsewhere, so they have to stay.
     * Bit of a trade-off I suppose but I like having the search in one place.
     * @param returnFunction what to do with each location
     * @param actor the actor to start from (also required in test conditions)
     * @param map current map
     * @param hasTarget predicate for testing locations
     * @param range depth to test to (how many iterations to do)
     * @param <ReturnType> Generic return type so that different things can be returned
     * @param returnFirst whether to return only the first thing found or not
     * @return A list of generic type
     */
    public static <ReturnType> List<ReturnType> searchAlgorithm(BiFunction<Actor, Location, List<ReturnType>> returnFunction, Actor actor, GameMap map, BiPredicate<Actor, Location> hasTarget, int range, boolean returnFirst)
    {
        Location startPoint = map.locationOf(actor);
        List<ReturnType> returnList = new ArrayList<>();
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
                if (hasTarget.test(actor, location)) //checks if the current location has a target object
                {
                    returnList.addAll(returnFunction.apply(actor, location));
                    if (returnFirst)
                    {
                        return returnList;
                    }
                }
                visitedLocations.add(location); //adds the location to the list of checked locations
            }
            locationsToVisit.removeAll(visitedLocations); //removes locations already checked/"visited"
            counter++;
        }
        return returnList;
    }

    /**
     * Just an easy way to have {@link #searchAlgorithm(BiFunction, Actor, GameMap, BiPredicate, int, boolean)} return a single object
     * @param returnFunction what to do with each location
     * @param actor the actor to start from (also required in test conditions)
     * @param map current map
     * @param hasTarget predicate for testing locations
     * @param range depth to test to (how many iterations to do)
     * @param <ReturnType> Generic return type so that different things can be returned
     * @param returnFirst whether to return only the first thing found or not
     * @return An object of generic type
     */
    static <ReturnType> ReturnType singleSearchAlgorithm(BiFunction<Actor, Location, List<ReturnType>> returnFunction, Actor actor, GameMap map, BiPredicate<Actor, Location> hasTarget, int range, boolean returnFirst)
    {
        return searchAlgorithm(returnFunction, actor, map, hasTarget, range, returnFirst).stream().findFirst().orElse(null);
    }
}