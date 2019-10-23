package game;

import edu.monash.fit2099.engine.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Class for a shop for buying and selling items
 */
public class Shop extends Ground
{

    private List<Item> items = new ArrayList<>();
    //TODO: Add some different stuff in here

    /**
     * Constructor for shop where actors (player) can buy and sell items, contains a list of all items which are able
     * to be purchased from the shop.
     */
    public Shop()
    {
        super('$');
        items.add(new DinosaurTag());
        items.add(new Egg(new Protoceratops("Protoceratops")));
        items.add(new CarnivoreFood());
        items.add(new HerbivoreFood());
        items.add(new Egg(new Velociraptor("Velociraptor")));
        items.add(new Egg(new Plesiosaur("Plesiosaur")));
        items.add(new Egg(new TRex("Tyrannosaurus Rex")));
        items.add(new SellableWeapon("Basic Sword", '/', 10, "slashes", 50, 1, WeaponType.MELEE));
        items.add(new TeleporterKit("Teleporter kit"));
        items.add(new WaterBoots("Water Boots"));
    }

    /**
     * Actors cannot enter shop
     */
    @Override
    public boolean canActorEnter(Actor actor) {
        return false;
    }

    /**
     * Generates all the possible SellActions from the items in the actor's inventory which can be sold and all the
     * BuyActions for all the items the actor is able to purchase.
     * @param actor Actor interacting with the shop
     * @param location location of the actor
     * @param Direction direction of the actor
     * @return collection of allowable actions the actor can perform on the shop
     */
    @Override
    public Actions allowableActions(Actor actor, Location location, String Direction)
    {
        Actions actions = new Actions();

        // Every sellable item in actor inventory can be sold
        for (Item item: actor.getInventory())
        {
            if (item instanceof Sellable)
            {
                actions.add(new SellAction(item));
            }
        }

        // Every item in the shop can be bought
        for (Item item: items)
        {
            actions.add(new BuyAction(item.copyItem()));
        }

        return actions;
    }

    @Override
    public String getName()
    {
        return "Shop";
    }
}
