package game;

import edu.monash.fit2099.engine.Item;
import edu.monash.fit2099.engine.WeaponItem;

/**
 * A weapon that can be bought and sold
 */
public class SellableWeapon extends WeaponItem implements Sellable
{
    private int value;
    /**
     * Constructor.
     *
     * @param name        name of the item
     * @param displayChar character to use for display when item is on the ground
     * @param damage      amount of damage this weapon does
     * @param verb        verb to use for this weapon, e.g. "hits", "zaps"
     */
    public SellableWeapon(String name, char displayChar, int damage, String verb, int value)
    {
        super(name, displayChar, damage, verb);
        this.value = value;
    }

    @Override
    public Item copyItem()
    {
        return new SellableWeapon(this.name, this.displayChar, this.damage(), this.verb(), this.value);
    }

    @Override
    public int getValue()
    {
        return value;
    }
}
