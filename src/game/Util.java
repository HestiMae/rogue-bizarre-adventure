package game;

import edu.monash.fit2099.engine.*;

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
}