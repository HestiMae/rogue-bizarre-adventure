package game;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.GameMap;
import edu.monash.fit2099.engine.Item;

/**
 * Allows players to buy things
 */
public class BuyAction extends Action
{
    private Item purchase; //the Item to be purchased

    /**
     * Simple constructor
     * @param purchase the item to be purchased
     */
    public BuyAction(Item purchase)
    {
        this.purchase = purchase;
    }

    /**
     * Adds the item to the players inventory, and deducts the value of the item from player's wallet
     * @param actor The actor performing the action.
     * @param map The map the actor is on.
     * @return a descriptive String
     */
    @Override
    public String execute(Actor actor, GameMap map)
    {
        if (((Player) actor).getWallet() >= ((Sellable) purchase).getValue()) //checks the player has enough money to make the purchase.
        {
            actor.addItemToInventory(purchase);
            ((Player) actor).removeMoney(((Sellable) purchase).getValue());
            return purchase.toString() + " bought by " + actor + " for $" + ((Sellable) purchase).getValue();
        }
        return actor + " does not have enough money";
    }

    @Override
    public String menuDescription(Actor actor)
    {
        return "Purchase " + purchase.toString() + " for $" + ((Sellable) purchase).getValue();
    }
}
