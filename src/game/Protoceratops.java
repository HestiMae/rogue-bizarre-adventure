package game;


import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actions;
import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.Display;
import edu.monash.fit2099.engine.DoNothingAction;
import edu.monash.fit2099.engine.GameMap;

import java.util.ArrayList;
import java.util.List;

/**
 * A herbivorous dinosaur.
 */
public class Protoceratops extends Dinosaur
{
    private static final int HIT_POINTS = 100;
    private static final char DISPLAY_CHAR = 'd';
    private List<Behaviour> behaviours;

    public Protoceratops(String name)
    {
        super(name, DISPLAY_CHAR, HIT_POINTS);
        List<Behaviour> behaviours = new ArrayList<>();
        behaviours.add(new WanderBehaviour());
        this.behaviours = behaviours;
    }

    /**
     * Figure out what to do next.
     * <p>
     * FIXME: Protoceratops wanders around at random, or if no suitable MoveActions are available, it
     * just stands there.  That's boring.
     *
     * @see edu.monash.fit2099.engine.Actor#playTurn(Actions, Action, GameMap, Display)
     */
    @Override
    public Action playTurn(Actions actions, Action lastAction, GameMap map, Display display)
    {
        Action wander = behaviours.get(0).getAction(this, map);
        if (wander != null)
            return wander;

        return new DoNothingAction();
    }

    @Override
    public String dinoType()
    {
        return "Protoceratops";
    }
}
