package game;

import edu.monash.fit2099.engine.*;
import java.util.ArrayList;
import java.util.List;

/**
 * This is a behaviour that looks for an adult bred t-rex in the map, and ends the game once it finds one.
 * This was the best way I could think to integrate it, a little hacky but at least it's inside a behaviour.
 */
public class EndGameBehaviour extends Action implements Behaviour
{
    @Override
    public Action getAction(Actor actor, GameMap map)
    {
        if (Util.singleSearchAlgorithm(this::addTRex, actor, map, this::hasTRex, map.getXRange().max(), true) != null)
        {
            return this;
        }
        return null;
    }

    @Override
    public Actions getAllActions(Actor actor, GameMap map)
    {
        Actions actions = new Actions();
        if (Util.singleSearchAlgorithm(this::addTRex, actor, map, this::hasTRex, map.getXRange().max(), true) != null)
        {
            actions.add(this);
        }
        return actions;
    }

    /**
     * Adds a T-Rex to a list once it gets found (yes I know its a little weird but its a consequence of generalising the search algorithm)
     * @param actor the actor performing the behaviour
     * @param location the current location
     * @return a List of found T-Rexes
     */
    private List<TRex> addTRex(Actor actor, Location location)
    {
        List<TRex> tRexes = new ArrayList<>();
        tRexes.add((TRex) location.getActor());
        return tRexes;
    }

    /**
     * Checks if a location has T-Rex that matches the conditions for victory.
     * @param actor the actor who the behaviour belongs to
     * @param location the location to search
     * @return true if found, false if not
     */
    private boolean hasTRex(Actor actor, Location location)
    {
        return location.getActor() instanceof TRex && ((TRex) location.getActor()).stage == DinoAge.ADULT && location.getActor().toString().contains("Jr.");
    }

    @Override
    public String execute(Actor actor, GameMap map)
    {
        map.removeActor(actor);
        return "Congratulations! You've successfully raised a T-Rex and ended the game!\nSee you soon :)";
    }

    @Override
    public String menuDescription(Actor actor)
    {
        return "You've raised a T-Rex! Well done. Select this to end the game";
    }
}
