package game;

import edu.monash.fit2099.engine.*;

/**
 * An action to allow the player to build (and destroy) types of ground on the map.
 */
public class BuildAction extends Action
{
    Location location;
    private String direction;
    Ground ground;

    /**
     * Simple constructor
     * @param location the location to build on
     * @param direction the direction from the actor
     * @param ground the Ground type to be built
     */
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
        // PERHAPS FIND A BETTER WAY TO ADD TELEPORTER LOCATIONS IF POSSIBLE
        if (ground instanceof Teleporter)
        {
            ((Teleporter) ground).addTeleporter(location);
        }
        return (!(ground instanceof Dirt)? "Player builds a " + ground.getName() : "Player destroys the " + oldGround)
                + " " + direction + " at: " + Util.locationString(location); //Uses the ternary operator to decide which String to return
    }

    @Override
    public String menuDescription(Actor actor)
    {
        return (!(ground instanceof Dirt)? "Build a " + ground.getName() : "Destroy the " + location.getGround().getName()) + " " + direction + " at: " + Util.locationString(location);
        //Uses the ternary operator to decide which String to return.
    }
}