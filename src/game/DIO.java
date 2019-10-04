package game;

import edu.monash.fit2099.engine.*;

import java.util.ArrayList;
import java.util.List;

public class DIO extends Actor
{
    private static final char DISPLAY_CHAR = 'D';
    private static final int HITPOINTS = 100;
    private List<Behaviour> behaviours;
    private Actor player;

    /**
     * Constructor.
     *
     * @param name        the name of the Actor
     */
    public DIO(String name, Actor player)
    {
        super(name, DISPLAY_CHAR, HITPOINTS);
        behaviours = new ArrayList<>();
        this.player = player;
        behaviours.add(new EnemyBehaviour(this.player, this));
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
    public Boolean isHealthy()
    {
        return hitPoints == maxHitPoints;
    }
}