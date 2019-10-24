package game;

import edu.monash.fit2099.engine.*;

import java.util.ArrayList;
import java.util.List;

/**
 * A class for simple creatures like Fish or Birds
 */
public abstract class SimpleCreature extends Actor implements Edible
{
    private List<Behaviour> behaviours;
    private boolean moveTwo;
    /**
     * Constructor.
     *
     * @param name        the name of the Actor
     * @param displayChar the character that will represent the Actor in the display
     * @param hitPoints   the Actor's starting hit points
     */
    public SimpleCreature(String name, char displayChar, int hitPoints, boolean moveTwo)
    {
        super(name, displayChar, hitPoints);
        behaviours = new ArrayList<>();
        behaviours.add(new WanderBehaviour());
        this.moveTwo = moveTwo;
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
    public boolean moveTwo()
    {
        return this.moveTwo;
    }
}
