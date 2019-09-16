package game;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.GameMap;
import edu.monash.fit2099.engine.Location;

public class BreedBehaviour implements Behaviour
{

    public BreedBehaviour()
    {

    }

    @Override
    public Action getAction(Actor actor, GameMap map)
    {
        if (!map.contains(actor))
        {
            return null;
        }

        Location here = map.locationOf(actor);
        // return new BreedAction();
        return null;
    }
}
