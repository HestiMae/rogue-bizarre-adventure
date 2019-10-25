package game;

import edu.monash.fit2099.engine.*;

import java.util.List;
import java.util.function.BiPredicate;

/**
 * A kind of Attack that hits all targets surrounding the actor.
 * Requires a weapon of Radial type.
 */
public class RadialAttackAction extends Action
{
    private Weapon weapon;
    /**
     * Constructor.
     *
     * @param weapon the weapon to attack with
     */
    public RadialAttackAction(Weapon weapon)
    {
        this.weapon = weapon;
    }

    @Override
    public String execute(Actor actor, GameMap map)
    {
        StringBuilder outString = new StringBuilder();
        for (Exit exit : map.locationOf(actor).getExits())
        {
            if (exit.getDestination().containsAnActor())
            {
                //executes and appends the string result of an attack for each actor found
                outString.append(new AttackAction(exit.getDestination().getActor(), weapon, map).execute(actor, map)).append("\n");
            }
        }
        return outString.toString();
    }

    @Override
    public String menuDescription(Actor actor)
    {
        return "Attack all foes around you using " + weapon;
    }
}
