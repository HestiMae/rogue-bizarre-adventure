package game;

import edu.monash.fit2099.engine.*;

/**
 * Class for item which is used to build teleporters
 */
public class TeleporterKit extends Item implements Sellable
{
    private static char DISPLAY_CHAR = 'k'; //display character for teleporter kit
    private static final boolean PORTABLE = true; //teleporter kits can be picked up

    /**
     * Constructor
     * @param name name of the teleporter kit
     */
    public TeleporterKit(String name)
    {
        super(name, DISPLAY_CHAR, PORTABLE);
    }

    /**
     * Copies the teleporter kit safely
     * @return a copy of the teleporter kit item
     */
    @Override
    public Item copyItem()
    {
        return new TeleporterKit(super.name);
    }

    /**
     * Gets the value of the teleporter kit
     * @return the value of the teleporter kit
     */
    @Override
    public int getValue()
    {
        return 10000;
    }

    /**
     * Each turn, adds an allowable action to build a teleporter using the kit if the actor is carrying it
     * @param location location of the teleporter
     * @param actor The actor carrying the teleporter kit
     */
    @Override
    public void tick(Location location, Actor actor)
    {
        this.allowableActions.clear();
        this.allowableActions.add(new BuildAction(location, "here", new Teleporter(location)));
    }
}
