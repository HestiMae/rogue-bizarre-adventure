package game;

import edu.monash.fit2099.engine.*;

import java.util.List;

public class RangedBehaviour implements Behaviour
{

    @Override
    public Action getAction(Actor actor, GameMap map)
    {
        return getAllActions(actor, map).getUnmodifiableActionList().stream().findFirst().orElse(null);
    }


    @Override
    public Actions getAllActions(Actor actor, GameMap map)
    {
        Actions actions = new Actions();
        int maxRange = actor.getInventory().stream().filter(item -> item instanceof SellableWeapon)
                .map(item -> (SellableWeapon) item)
                .map(SellableWeapon::getRange).reduce(Integer::max).orElse(-1); //finds the weapon with the highest range in player inventory
        actions.add(Util.searchAlgorithm(this::returnFunction, actor, map, this::hasTarget, maxRange));
        return actions;
    }

    boolean hasTarget(Actor actor, Location location)
    {
        return location.containsAnActor() && !location.getActor().getClass().isAssignableFrom(actor.getClass()); //Checks if there is an actor at the location and that its not of the same class
    }

    private List<Action> returnFunction(Actor actor, Location location)
    {
        Actions actions = new Actions();
        int currRange = Util.distance(location.map().locationOf(actor), location);
        actor.getInventory().stream()
                .filter(item -> item instanceof SellableWeapon)
                .filter(item -> ((SellableWeapon) item).getRange() >= currRange)
                .forEach(item -> actions.add(new AttackAction(location.getActor(), (Weapon) item)));
        return actions.getUnmodifiableActionList();
    }
}