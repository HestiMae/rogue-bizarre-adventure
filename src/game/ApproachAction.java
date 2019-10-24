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
    private boolean move2;

    /**
     * Constructor
     * @param end the end location
     */
    public ApproachAction(Location end, boolean move2)
    {
        this.end = end;
        this.move2 = move2;
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
                    if (move2)
                    {
                        for (Exit exit1 : destination.getExits())
                        {
                            int currentDistance2 = Util.distance(destination, end);
                            Location destination2 = exit1.getDestination();
                            if (destination2.canActorEnter(actor))
                            {
                                int newDistance2 = Util.distance(destination2, end);
                                if (newDistance2 < currentDistance2)
                                {
                                    map.moveActor(actor, destination2);
                                    direction = exit1.getName();
                                    return menuDescription(actor);
                                }
                            }
                        }
                    }
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
