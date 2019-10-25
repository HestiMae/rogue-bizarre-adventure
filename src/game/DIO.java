package game;

import edu.monash.fit2099.engine.*;

import java.util.Arrays;


/**
 * A kind of enemy (name is a JoJo reference)
 */
public class DIO extends Enemy
{
    private static final char DISPLAY_CHAR = 'D';
    private static final int HITPOINTS = 100;
    private static final int STANDDAMAGE = 30;
    private static final int LIFETIME = 20; //How many turns DIO will exist for (I chose to have this so that it was possible to just avoid fighting)
    private int turnsAlive = 0; //the number of turns DIO has been in the map

    /**
     * Constructor.
     *
     * @param name        the name of the Actor
     */
    public DIO(String name, Actor player)
    {
        super(name, DISPLAY_CHAR, HITPOINTS, player, false);
        Stand stand = new Stand("The World", 'W', STANDDAMAGE, "Muda Muda Muda", 1000, 1, Arrays.asList(WeaponType.MELEE, WeaponType.RADIAL));
        inventory.add(stand);
    }

    @Override
    public Action playTurn(Actions actions, Action lastAction, GameMap map, Display display)
    {
        turnsAlive++;
        if (turnsAlive < LIFETIME)
        {
            super.playTurn(actions, lastAction, map, display);
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
}