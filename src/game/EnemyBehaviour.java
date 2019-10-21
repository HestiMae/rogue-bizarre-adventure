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
        Location startPoint = map.locationOf(enemy);
        for (Exit exit : startPoint.getExits())
        {
            if (exit.getDestination().getActor() == this.player)
            {

                return new AttackAction(player);
            }
        }
        return null;
    }

    @Override
    public Actions getAllActions(Actor actor, GameMap map)
    {
        return new Actions(getAction(actor, map));
    }
}
