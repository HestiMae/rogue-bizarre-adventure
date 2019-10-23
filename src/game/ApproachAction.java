package game;

import edu.monash.fit2099.engine.*;


import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Steps one tile closer to the target location
 * Oh? You're approaching me?
 */
public class ApproachAction extends Action
{
    private int maxDistance;
    private Location end;
    private String direction;

    /**
     * Constructor
     * @param end the end location
     */
    public ApproachAction(Location end, int maxDistance)
    {
        this.end = end;
        this.maxDistance = maxDistance;
    }

    @Override
    public String execute(Actor actor, GameMap map)
    {
        //Finds a move decreasing the distance between 2 locations
         Util.searchAlgorithm(this::distanceGetter, actor, map, this::hasTarget, maxDistance);
         return menuDescription(actor);
    }

    @Override
    public String menuDescription(Actor actor)
    {
        return actor + " moves " + direction;
    }

    private boolean hasTarget(Actor actor, Location location)
    {
        return location.canActorEnter(actor);
    }

    private List<String> distanceGetter(Actor actor, Location location)
    {
        int currentDistance = Util.distance(location.map().locationOf(actor), end);
        int newDistance = Util.distance(location, end);
        List<String> returnList = new ArrayList<>();
        if (newDistance < currentDistance)
        {
            location.map().moveActor(actor, location);
            direction = location.map().locationOf(actor).getExits().stream()
                    .filter(exit -> exit.getDestination().equals(location))
                    .collect(Collectors.toList()).get(0).getName();
            returnList.add(menuDescription(actor));
        }
        return returnList;
    }
}
