package game;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.GameMap;
import edu.monash.fit2099.engine.Location;

/**
 * Class for action to teleport an actor from one location to another
 */
public class TeleportAction extends Action
{
    Location location;

    /**
     * constructor
     * @param location location to teleport to
     */
    public TeleportAction(Location location)
    {
        this.location = location;
    }

    /**
     * Executes the actor to teleport them
     * @param actor The actor performing the action.
     * @param map The map the actor is on.
     * @return The string stating that result of the action
     */
    @Override
    public String execute(Actor actor, GameMap map)
    {
        // Only teleport if there is not another actor at the location
        if (!(location.map().isAnActorAt(location)))
        {
            Location actorLocation = map.locationOf(actor);
            map.moveActor(actor, this.location);
            return actor + " teleported from " + actorLocation + " to " + "(" + location.x() + "," + location.y() + ")";
        }
        return "There ia already another actor at " + "(" + location.x() + "," + location.y() + ")";
    }

    /**
     * Menu descriptor for the action
     * @param actor The actor performing the action.
     * @return String stating the option to perform the action
     */
    @Override
    public String menuDescription(Actor actor)
    {
        return actor + " teleport to " + "(" + location.x() + "," + location.y() + ")";
    }
}
