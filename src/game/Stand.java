package game;

import edu.monash.fit2099.engine.Item;

import java.util.ArrayList;
import java.util.List;

/**
 * A special kind of weapon that offers multiple kinds of attack.
 * Another reference to JoJos Bizarre Adventure.
 */
public class Stand extends SellableWeapon
{
    private String attackVerb;
    private List<WeaponType> attackTypes; //the kinds of attacks this stand can do - ranged, radial, or melee
    /**
     * Constructor.
     *
     * @param name        name of the item
     * @param displayChar character to use for display when item is on the ground
     * @param damage      amount of damage this weapon does
     */
    public Stand(String name, char displayChar, int damage, String attackVerb, int value, int range, List<WeaponType> types)
    {
        super(name, displayChar, damage, "", value, range, types.get(0));
        this.attackTypes = types;
        this.attackVerb = attackVerb;
        this.portable = false; //Stands cannot be dropped
    }

    @Override
    public Item copyItem()
    {
        return new Stand(this.name, this.displayChar, this.damage(), this.attackVerb, this.getValue(), this.getRange(), this.attackTypes);
    }

    @Override
    public String verb()
    {
        return "uses their " + name + " to " + attackVerb;
    }

    public List<WeaponType> getTypes()
    {
        return attackTypes;
    }

}
