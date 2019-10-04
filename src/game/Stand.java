package game;

import edu.monash.fit2099.engine.Item;
import edu.monash.fit2099.engine.WeaponItem;

public class Stand extends WeaponItem
{
    private String attackVerb;
    /**
     * Constructor.
     *
     * @param name        name of the item
     * @param displayChar character to use for display when item is on the ground
     * @param damage      amount of damage this weapon does
     */
    public Stand(String name, char displayChar, int damage, String attackVerb)
    {
        super(name, displayChar, damage, "");
        this.attackVerb = attackVerb;
    }

    @Override
    public Item copyItem()
    {
        return null;
    }

    @Override
    public String verb()
    {
        return "uses their " + name + " to " + attackVerb;
    }
}
