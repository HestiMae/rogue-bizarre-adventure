package game;

import edu.monash.fit2099.engine.*;

import java.util.ArrayList;

public class Shop extends Ground
{

    ArrayList<Item> items = new ArrayList<Item>();

    /**
     * Constructor for shop where actors (player) can buy and sell items, contains a list of all items which are able
     * to be purchased from the shop.
     */
    public Shop()
    {
        super('$');
        items.add(new DinosaurTag());
        items.add(new Egg(new Protoceratops("SHOP")));
        items.add(new CarnivoreFood());
        items.add(new Egg(new Velociraptor("SHOP")));
        // NEED TO ADD MORE ITEMS LATER
    }

    /**
     * Generates all the possible SellActions from the items in the actor's inventory which can be sold and all the
     * BuyActions for all the items the actor is able to purchase.
     * @param actor Actor interacting with the shop
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
            actions.add(new BuyAction(item));
        }

        return actions;
    }
}
