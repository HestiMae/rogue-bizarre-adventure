package game;

import edu.monash.fit2099.engine.Item;

/**
 * Class for water boots which allows an actor to walk on water
 */
public class WaterBoots extends Item implements Sellable
{
    private static final char DISPLAY_CHAR = 'b'; //water boots displayed by b
    private static final boolean PORTABLE = true; //water boots are portable
    public static final int COST = 10000; //water boots cost 10000

    /***
     * Constructor.
     *  @param name the name of this item
     */
    public WaterBoots(String name)
    {
        super(name, DISPLAY_CHAR, PORTABLE);
        this.addSkill(PassableTerrain.WATER);
    }

    /**
     * Copy constructor for water boots
     * @return copy of water boot item
     */
    @Override
    public Item copyItem()
    {
        return new WaterBoots(super.name);
    }

    /**
     * Gets the value of the water boots
     * @return value of the water boots
     */
    @Override
    public int getValue()
    {
        return COST;
    }
}
