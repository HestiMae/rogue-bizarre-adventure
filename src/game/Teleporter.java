package game;

import edu.monash.fit2099.engine.Actions;
import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.Ground;
import edu.monash.fit2099.engine.Location;

import java.util.ArrayList;
import java.util.List;

/**
 * Class for teleporter ground type which can be accessed in a network of locations
 */
public class Teleporter extends Ground
{
    private static List<Location> locations = new ArrayList<Location>();
    private final Location location;

    /**
     * Constructor for teleporter
     * @param location location of the teleporter
     */
    public Teleporter(Location location) {
        super('=');
        this.location = location;
    }

    /**
     * Gets the name of the teleporter
     * @return name of the teleporter
     */
    @Override
    public String getName() { return "Teleporter"; }

    /**
     * Gets all of the allowable teleport locations from the current teleporter
     * @param actor the Actor acting
     * @param location location of the actor
     * @param direction direction of the actor
     * @return the Teleport actions to all other teleporters on the map
     */
    @Override
    public Actions allowableActions(Actor actor, Location location, String direction)
    {
        Actions actions = new Actions();

        // From one teleporter, all other teleporters should be accessible
        for (Location teleporter: locations)
        {
            if (!teleporter.equals(this.location))
            {
                actions.add(new TeleportAction(teleporter));
            }
        }

        return actions;
    }

    /**
     * Adds the location of a teleporter to the static array of teleporter locations
     * @param location the location of the teleporter to be added
     */
    public void addTeleporter(Location location)
    {
        locations.add(location);
    }
}
