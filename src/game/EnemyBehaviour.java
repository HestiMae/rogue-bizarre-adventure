package game;

import edu.monash.fit2099.engine.*;

public class EnemyBehaviour implements Behaviour
{
    private Actor player;
    private Actor enemy;

    public EnemyBehaviour(Actor player, Actor enemy)
    {
        this.enemy = enemy;
        this.player = player;
    }
    @Override
    public Action getAction(Actor actor, GameMap map)
    {
        if (!nextToTarget(enemy, map))
        {
            return new ApproachAction(map.locationOf(enemy), map.locationOf(player));
        }
        return new AttackAction(player);
    }

    private boolean nextToTarget(Actor enemy, GameMap map)
    {
        Location startPoint = map.locationOf(enemy);
        for (Exit exit : startPoint.getExits())
        {
            if (exit.getDestination().getActor() instanceof Player)
            {
                return true;
            }
        }
        return false;
    }
}
