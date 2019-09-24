package game;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.GameMap;
import edu.monash.fit2099.engine.Item;

public class BuyAction extends Action
{
    private Item purchase;

    public BuyAction(Item purchase)
    {
        this.purchase = purchase;
    }

    @Override
    public String execute(Actor actor, GameMap map)
    {
        if (((Player) actor).getWallet() >= ((Sellable) purchase).getValue())
        {
            actor.addItemToInventory(purchase);
            ((Player) actor).removeMoney(((Sellable) purchase).getValue());
            return purchase.toString() + " bought by player for " + ((Sellable) purchase).getValue();
        }
        return actor + " does not have enough money";
    }

    @Override
    public String menuDescription(Actor actor)
    {
        return "Purchase " + purchase.toString() + " for " + ((Sellable) purchase).getValue();
    }
}
