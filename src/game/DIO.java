package game;

import edu.monash.fit2099.engine.*;


public class DIO extends Enemy
{
    private static final char DISPLAY_CHAR = 'D';
    private static final int HITPOINTS = 100;
    private static final int STANDDAMAGE = 30;
    private Stand stand;

    /**
     * Constructor.
     *
     * @param name        the name of the Actor
     */
    public DIO(String name, Actor player)
    {
        super(name, DISPLAY_CHAR, HITPOINTS, player);
        this.stand = new Stand("The World", 'W', STANDDAMAGE, "Muda Muda Muda");
        inventory.add(this.stand);
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