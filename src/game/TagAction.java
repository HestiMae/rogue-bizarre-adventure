package game;

import edu.monash.fit2099.engine.*;

import java.util.List;

public class TagAction extends Action
{
    protected Dinosaur target;

    /**
     * Constructor
     * @param target target actor to be tagged
     */
    public TagAction(Dinosaur target)
    {
        this.target = target;
    }

    /**
     * Checks if a dinosaur tag is in an Actor's inventory
     * @param actor the actor whose inventory is checked
     * @return true if a dinosaur tag is found, false otherwise
     */
    public Boolean getTag(Actor actor)
    {
        List<Item> items = actor.getInventory();
        for (Item item: items)
        {
            if (item instanceof DinosaurTag)
            {
                actor.removeItemFromInventory(item);
                return true;
            }
        }
        return false;
    }

    /**
     * Executes the tag action which consumes a tag from the actor's inventory and sells the target for its associated
     * value. Can only be completed if the player is holding a tag.
     * @param actor The actor performing the action.
     * @param map The map the actor is on.
     * @return String to be displayed based on the outcome of the action
     */
    @Override
    public String execute(Actor actor, GameMap map)
    {

        if (getTag(actor))
        {
            //value = target.getValue(); NEEDS TO BE FIXED
            map.removeActor(target);
            ((Player) actor).addMoney(target.getValue());
            return target + " was sold for " + target.getValue();
        }

        return "Tag is missing from " + actor + " inventory";
    }

    @Override
    public String menuDescription(Actor actor)
    {
        return actor + " tries to tag " + target;
    }
}
