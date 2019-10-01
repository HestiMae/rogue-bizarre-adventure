package game;

import edu.monash.fit2099.engine.*;

/**
 * An action to allow the player to build (and destroy) types of ground on the map.
 */
public class BuildAction extends Action
{
    Location location;
    String direction;
    Ground ground;

    public BuildAction(Location location, String direction, Ground ground)
    {
        this.location = location;
        this.direction = direction;
        this.ground = ground;
    }

    /**
     * Builds the chosen type of ground at a location. Building dirt is treated as destroying structures.
     * @param actor The actor performing the action.
     * @param map The map the actor is on.
     * @return descriptive string for the action.
     */
    @Override
    public String execute(Actor actor, GameMap map)
    {
        String oldGround = location.getGround().getName();
        location.setGround(ground);
        return (!(ground instanceof Dirt)? "Player builds a " + ground.getName() : "Player destroys the " + oldGround)
                + " to the " + direction + " at: (" + location.x() + ", " + location.y() + ")";
    }

    @Override
    public String menuDescription(Actor actor)
    {
        return (!(ground instanceof Dirt)? "Build a " + ground.getName() : "Destroy the " + location.getGround().getName()) + " to the " + direction + " at: (" + location.x() + ", " + location.y() + ")";
    }
}