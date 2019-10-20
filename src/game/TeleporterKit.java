package game;

import edu.monash.fit2099.engine.*;

public class TeleporterKit extends Item implements Sellable
{
    private static char DISPLAY_CHAR = 'k'; //display character for teleporter kit
    private static final boolean PORTABLE = true; //teleporter kits can be picked up

    /***
     * Constructor.
     *  @param name the name of this copyItem
     */
    public TeleporterKit(String name)
    {
        super(name, DISPLAY_CHAR, PORTABLE);
    }

    @Override
    public Item copyItem()
    {
        return new TeleporterKit(super.name);
    }

    @Override
    public int getValue()
    {
        return 3000;
    }

    @Override
    public void tick(Location location, Actor actor)
    {
        this.allowableActions.clear();
        this.allowableActions.add(new BuildAction(location, "here", new Teleporter(location)));
    }
}
