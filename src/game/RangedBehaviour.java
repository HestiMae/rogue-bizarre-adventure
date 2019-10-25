package game;

import edu.monash.fit2099.engine.*;

import java.util.List;

/**
 * A behaviour that allows an actor to perform ranged attacks.
 */
public class RangedBehaviour implements Behaviour
{

    @Override
    public Action getAction(Actor actor, GameMap map)
    {
        //for when you only want one Attack action returned
        return getAllActions(actor, map).getUnmodifiableActionList().stream().findFirst().orElse(null);
    }


    @Override
    public Actions getAllActions(Actor actor, GameMap map)
    {
        Actions actions = new Actions();
        int maxRange = actor.getInventory().stream().filter(item -> item instanceof SellableWeapon)
                .map(item -> (SellableWeapon) item)
                .map(SellableWeapon::getRange).reduce(Integer::max).orElse(-1); //finds the weapon with the highest range in player inventory
        actions.add(Util.searchAlgorithm(this::returnFunction, actor, map, this::hasTarget, maxRange, false));
        return actions;
    }

    /**
     * Checks if there is an actor at the location and that the performing actor is allowed to attack them
     * Used in the search algorithm
     * @param actor the actor performing the behaviour
     * @param location the location to check
     * @return true if the actor meets the conditions, false if not.
     */
    boolean hasTarget(Actor actor, Location location)
    {
        return location.containsAnActor() && actor.canAttack(location.getActor());
    }

    /**
     * What to do when an actor is found.
     * In this case, returns a list of Attack Actions, one for each ranged weapon in the actors inventory,
     * that is still in range (we keep track of how far out the search has gotten, so that weapons can have effective ranges)
     * @param actor the actor performing the behaviour
     * @param location the location to check
     * @return a list of attack actions
     */
    private List<Action> returnFunction(Actor actor, Location location)
    {
        Actions actions = new Actions();
        int currRange = Util.distance(location.map().locationOf(actor), location);
        actor.getInventory().stream()
                .filter(item -> item instanceof SellableWeapon)
                .filter(item -> ((SellableWeapon) item).getRange() >= currRange)
                .forEach(item -> actions.add(new AttackAction(location.getActor(), (Weapon) item, location.map())));
        return actions.getUnmodifiableActionList();
    }
}