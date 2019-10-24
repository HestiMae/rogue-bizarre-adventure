package game;

import edu.monash.fit2099.engine.*;
import java.util.ArrayList;
import java.util.List;

public class EndGameBehaviour extends Action implements Behaviour
{
    @Override
    public Action getAction(Actor actor, GameMap map)
    {
        if (Util.singleSearchAlgorithm(this::findTRex, actor, map, this::hasTRex, map.getXRange().max(), true) != null)
        {
            return this;
        }
        return null;
    }

    @Override
    public Actions getAllActions(Actor actor, GameMap map)
    {
        Actions actions = new Actions();
        if (Util.singleSearchAlgorithm(this::findTRex, actor, map, this::hasTRex, map.getXRange().max(), true) != null)
        {
            actions.add(this);
        }
        return actions;
    }

    private List<TRex> findTRex(Actor actor, Location location)
    {
        List<TRex> tRexes = new ArrayList<>();
        tRexes.add((TRex) location.getActor());
        return tRexes;
    }

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
