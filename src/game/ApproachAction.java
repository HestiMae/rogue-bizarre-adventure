package game;

import edu.monash.fit2099.engine.*;

/**
 * Steps one tile closer to the target location
 * Oh? You're approaching me?
 */
public class ApproachAction extends Action
{
    private Location end;
    private String direction;

    /**
     * Constructor
     * @param end the end location
     */
    public ApproachAction(Location end)
    {
        this.end = end;
    }

    @Override
    public String execute(Actor actor, GameMap map)
    {
        //Basically the code from FollowBehaviour. Finds a move that decreases the distance between the 2 locations.
        Location start = map.locationOf(actor);
        int currentDistance = Util.distance(start, end);
        for (Exit exit : start.getExits())
        {
            Location destination = exit.getDestination();
            if (destination.canActorEnter(actor))
            {
                int newDistance = Util.distance(destination, end);
                if (newDistance < currentDistance)
                {
                    map.moveActor(actor, destination);
                    direction = exit.getName();
                    return menuDescription(actor);
                }
            }
        }
        for (Exit exit : start.getExits())
        {
            Location destination = exit.getDestination();
            if (destination.canActorEnter(actor))
            {
                int newDistance = Util.distance(destination, end);
                if (newDistance <= currentDistance)
                {
                    map.moveActor(actor, destination);
                    direction = exit.getName();
                    return menuDescription(actor);
                }
            }
        }
        return actor + " tried to approach their target at (" + end.x() + ", " + end.y() + "), but it couldn't get any closer.";
    }

    @Override
    public String menuDescription(Actor actor)
    {
        return actor + " moves " + direction;
    }
}
