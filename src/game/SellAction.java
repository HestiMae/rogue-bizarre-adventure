package game;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.GameMap;
import edu.monash.fit2099.engine.Item;

/**
 * An action to sell an item.
 */
public class SellAction extends Action
{
    private Item sellItem;

    public SellAction(Item sellItem)
    {
        this.sellItem = sellItem;
    }

    @Override
    public String execute(Actor actor, GameMap map)
    {
        ((Player) actor).addMoney(((Sellable) sellItem).getValue());
        actor.removeItemFromInventory(sellItem);
        return sellItem.toString() + " sold by player for $" + ((Sellable) sellItem).getValue();
    }

    @Override
    public String menuDescription(Actor actor)
    {
        return "Sell " + sellItem.toString() + " for $" + ((Sellable) sellItem).getValue();
    }
}
