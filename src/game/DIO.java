package game;

import edu.monash.fit2099.engine.*;


public class DIO extends Enemy
{
    private static final char DISPLAY_CHAR = 'D';
    private static final int HITPOINTS = 100;
    private static final int STANDDAMAGE = 30;
    private static final int LIFETIME = 20;
    private int turnsAlive = 0;

    /**
     * Constructor.
     *
     * @param name        the name of the Actor
     */
    public DIO(String name, Actor player)
    {
        super(name, DISPLAY_CHAR, HITPOINTS, player);
        Stand stand = new Stand("The World", 'W', STANDDAMAGE, "Muda Muda Muda");
        inventory.add(stand);
    }

    @Override
    public Action playTurn(Actions actions, Action lastAction, GameMap map, Display display)
    {
        turnsAlive++;
        if (turnsAlive < LIFETIME)
        {
            for (Behaviour b : behaviours)
            {
                Action outAction = b.getAction(this, map);
                if (outAction != null)
                {
                    return outAction;
                }
            }
        }
        else
        {
            return new RemoveActorAction();
        }
        return new DoNothingAction();
    }

    @Override
    public Boolean isHealthy()
    {
        return hitPoints == maxHitPoints;
    }

    @Override
    public Boolean isFlying()
    {
        return false;
    }
}