package game;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.Exit;
import edu.monash.fit2099.engine.GameMap;

import java.util.List;

public class RadialAttackAction extends Action
{
    private List<Actor> targets;
    /**
     * Constructor.
     *
     * @param targets the Actors to attack
     */
    public RadialAttackAction(List<Actor> targets)
    {
        this.targets = targets;
    }

    @Override
    public String execute(Actor actor, GameMap map)
    {
        StringBuilder outString = new StringBuilder();
        for (Actor target : targets)
        {
            outString.append(new AttackAction(target).execute(actor, map)).append("\n");
        }
        return outString.toString();
    }

    @Override
    public String menuDescription(Actor actor)
    {
        return null;
    }
}
