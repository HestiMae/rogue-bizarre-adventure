package game;

import edu.monash.fit2099.engine.*;

import java.util.ArrayList;
import java.util.List;

public abstract class Enemy extends Actor
{
    List<Behaviour> behaviours;
    protected Actor player;
    /**
     * Constructor.
     *
     * @param name        the name of the Actor
     * @param displayChar the character that will represent the Actor in the display
     * @param hitPoints   the Actor's starting hit points
     */
    public Enemy(String name, char displayChar, int hitPoints, Actor player)
    {
        super(name, displayChar, hitPoints);
        this.player = player;
        behaviours = new ArrayList<>();
        behaviours.add(new EnemyBehaviour(this, player));
        behaviours.add(new WanderBehaviour());
    }

    @Override
    public Action playTurn(Actions actions, Action lastAction, GameMap map, Display display)
    {
        for (Behaviour b : behaviours)
        {
            Action outAction = b.getAction(this, map);
            if (outAction != null)
            {
                return outAction;
            }
        }
        return new DoNothingAction();
    }

    @Override
    public int getHP()
    {
        return hitPoints;
    }

    @Override
    public void addBehaviour(Behaviour behaviour)
    {
        behaviours.add(behaviour);
    }

    @Override
    public boolean hasBehaviour(Behaviour behaviour)
    {
        return behaviours.stream().anyMatch(behaviour1 -> behaviour.getClass().equals(behaviour1.getClass()));
    }

    @Override
    public Actions getAllowableActions(Actor otherActor, String direction, GameMap map)
    {
        Actions actions = new Actions();
        actions.add(new AttackAction(this));
        return actions;
    }
}
