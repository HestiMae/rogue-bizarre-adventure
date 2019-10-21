package game;

import edu.monash.fit2099.engine.*;

import java.util.List;
import java.util.function.BiPredicate;

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
                outString.append(new AttackAction(exit.getDestination().getActor(), weapon).execute(actor, map)).append("\n");
            }
        }
        return outString.toString();
    }

    @Override
    public String menuDescription(Actor actor)
    {
        return "Attack all foes around you";
    }
}
