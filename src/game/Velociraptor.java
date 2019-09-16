package game;

import edu.monash.fit2099.engine.*;

import java.util.ArrayList;
import java.util.List;

public class Velociraptor extends Dinosaur
{
    private static final int HIT_POINTS = 75;
    private static final char DISPLAY_CHAR = 'r';
    private List<Behaviour> behaviours;

    public Velociraptor(String name)
    {
        super(name, DISPLAY_CHAR, HIT_POINTS);
        List<Behaviour> behaviours = new ArrayList<>();
        behaviours.add(new WanderBehaviour());
        this.behaviours = behaviours;
    }

    /**
     * Figure out what to do next.
     * <p>
     * FIXME: Velociraptor wanders around at random, or if no suitable MoveActions are available, it
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
        return "Velociraptor";
    }
}
