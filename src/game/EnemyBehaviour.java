package game;

import edu.monash.fit2099.engine.*;

/**
 * Default behaviour for enemies
 */
public class EnemyBehaviour implements Behaviour
{
    private Actor player;
    private Actor enemy;

    public EnemyBehaviour(Actor enemy, Actor player)
    {
        this.enemy = enemy;
        this.player = player;
    }
    @Override
    public Action getAction(Actor actor, GameMap map)
    {
        if (!nextToTarget(enemy, map))
        {
            return new ApproachAction(map.locationOf(player));
        }
        return new AttackAction(player);
    }

    private boolean nextToTarget(Actor enemy, GameMap map)
    {
        Location startPoint = map.locationOf(enemy);
        for (Exit exit : startPoint.getExits())
        {
            if (exit.getDestination().getActor() == this.player)
            {
                return true;
            }
        }
        return false;
    }
}
