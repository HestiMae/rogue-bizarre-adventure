package game;

import edu.monash.fit2099.engine.*;

import java.util.Arrays;
import java.util.Random;


/**
 * A kind of enemy (name is a JoJo reference)
 */
public class DIO extends Enemy
{
    private static final char DISPLAY_CHAR = 'D';
    private static final int HITPOINTS = 300;
    private static final int STANDDAMAGE = 30;
    private static final int LIFETIME = 20; //How many turns DIO will exist for (I chose to have this so that it was possible to just avoid fighting)
    private int turnsAlive = 0; //the number of turns DIO has been in the map
    private Random random;
    private float timeStopChance = 0.1f;
    private int timeStopTurns = 3;
    private ZaWarudo zaWarudo; //a reference to the World (for the purpose of stopping time, DIOs signature move in JoJos)

    /**
     * Constructor.
     *
     * @param name        the name of the Actor
     */
    public DIO(String name, Actor player, ZaWarudo zaWarudo)
    {
        super(name, DISPLAY_CHAR, HITPOINTS, player, false);
        Stand stand = new Stand("The World", 'W', STANDDAMAGE, "Muda Muda Muda", 1000, 1, Arrays.asList(WeaponType.MELEE, WeaponType.RADIAL));
        inventory.add(stand);
        addSkill(PassableTerrain.LAND);
        this.random = new Random();
        this.zaWarudo = zaWarudo;
    }

    @Override
    public Action playTurn(Actions actions, Action lastAction, GameMap map, Display display)
    {
        turnsAlive++;
        if (turnsAlive < LIFETIME)
        {
            if (random.nextFloat() < timeStopChance && zaWarudo.getTimeStopTurns() == 0)
            {
                zaWarudo.stopTime(this, timeStopTurns);
            }
            return super.playTurn(actions, lastAction, map, display);
        }
        else
        {
            return new RemoveActorAction();
        }
    }

    @Override
    public Boolean isHealthy()
    {
        return hitPoints == maxHitPoints;
    }
}