package game;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.GameMap;
import edu.monash.fit2099.engine.Item;

/**
 * Class for feed action which allows an actor to be able to feed a target dinosaur with an edible item
 */
public class FeedAction extends Action
{
    private Dinosaur target;
    private Edible food;

    /**
     * Constructor for feed action
     * @param target dinosaur to be fed
     * @param food edible item to be fed
     */
    public FeedAction(Dinosaur target, Edible food)
    {
        this.target = target;
        this.food = food;
    }

    /**
     * Executing the feed action which replenishes target metabolise level by the food value and removes the food item from
     * the performing actor's inventory
     * @param actor The actor performing the action.
     * @param map The map the actor is on.
     * @return message signifying the acton
     */
    @Override
    public String execute(Actor actor, GameMap map)
    {
        actor.removeItemFromInventory((Item) food);
        target.eat(food);
        return actor + " feeds " + target + " " + food + " " + target + " new hunger level is " + target.getFoodValue();
    }

    /**
     * The string to be displayed on the menu when the action is available
     * @param actor The actor performing the action.
     * @return string representing thr action on the menu
     */
    @Override
    public String menuDescription(Actor actor)
    {
        return actor + " feeds " + target + " " + food;
    }
}
