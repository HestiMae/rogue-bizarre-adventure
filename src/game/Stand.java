package game;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Item;
import edu.monash.fit2099.engine.WeaponItem;

import java.util.ArrayList;
import java.util.List;

public class Stand extends SellableWeapon
{
    private String attackVerb;
    /**
     * Constructor.
     *
     * @param name        name of the item
     * @param displayChar character to use for display when item is on the ground
     * @param damage      amount of damage this weapon does
     */
    public Stand(String name, char displayChar, int damage, String attackVerb, int value, int range, WeaponType type)
    {
        super(name, displayChar, damage, "", value, range, type);
        this.attackVerb = attackVerb;
        this.portable = false; //Stands cannot be dropped
    }

    @Override
    public Item copyItem()
    {
        return new Stand(this.name, this.displayChar, this.damage(), this.attackVerb, this.getValue(), this.getRange(), this.type);
    }

    @Override
    public String verb()
    {
        return "uses their " + name + " to " + attackVerb;
    }

    public WeaponType getType()
    {
        return this.type;
    }

}
