package game;

import edu.monash.fit2099.engine.*;

public class ApproachAction extends Action
{
    private Location start;
    private Location end;
    private String direction;

    /**
     * Constructor
     * @param start the start location
     * @param end the end location
     */
    public ApproachAction(Location start, Location end)
    {
        this.start = start;
        this.end = end;
        this.direction = null;

    }

    @Override
    public String execute(Actor actor, GameMap map)
    {
        //Basically the code from FollowBehaviour. Finds a move that decreases the distance between the 2 locations.
        int currentDistance = Util.distance(start, end);
        for (Exit exit : start.getExits())
        {
            Location destination = exit.getDestination();
            if (destination.canActorEnter(actor))
            {
                int newDistance = Util.distance(start, destination);
                if (newDistance < currentDistance)
                {
                    map.moveActor(actor, destination);
                    direction = exit.getName();
                    return menuDescription(actor);
                }
            }
        }
        return null;
    }

    @Override
    public String menuDescription(Actor actor)
    {
        return actor + " moves " + direction;
    }
}
