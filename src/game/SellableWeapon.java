package game;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actions;
import edu.monash.fit2099.engine.Item;
import edu.monash.fit2099.engine.WeaponItem;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * A weapon that can be bought and sold
 */
public class SellableWeapon extends WeaponItem implements Sellable
{
    protected WeaponType type;
    private int value;
    private int range; //how far away a target can be: 1 for melee
    private TextMap attackVerbs = new TextMap();
    /**
     * Constructor.
     *
     * @param name        name of the item
     * @param displayChar character to use for display when item is on the ground
     * @param damage      amount of damage this weapon does
     * @param verb        verb to use for this weapon, e.g. "hits", "zaps"
     */
    public SellableWeapon(String name, char displayChar, int damage, String verb, int value, int range, WeaponType type)
    {
        super(name, displayChar, damage, verb);
        this.value = value;
        this.type = type;
        this.range = range;
        this.attackVerbs = new TextMap();
        attackVerbs.addEntries("attacks", Arrays.asList("slashes", "strikes", "maims", "vibe checks", "yeets"));
    }

    @Override
    public Item copyItem()
    {
        return new SellableWeapon(this.name, this.displayChar, this.damage(), this.verb(), this.value, this.range, this.type);
    }

    @Override
    public int getValue()
    {
        return value;
    }

    public WeaponType getType()
    {
        return this.type;
    }

    @Override
    public List<Action> getAllowableActions()
    {
        List<Action> actions = new ArrayList<>();
        if (this.type == WeaponType.RADIAL || (this instanceof Stand && ((Stand) this).getTypes().contains(WeaponType.RADIAL)))
        {
            actions.add(new RadialAttackAction(this));
        }
        return actions;
    }

    public int getRange()
    {
        return range;
    }

    @Override
    public String verb()
    {
        return attackVerbs.randomText("attacks");
    }
}
