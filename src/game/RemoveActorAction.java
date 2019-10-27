package game;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.GameMap;

/**
 * for when you want to remove an actor and have something appear in the menu
 */
public class RemoveActorAction extends Action
{
    @Override
    public String execute(Actor actor, GameMap map)
    {
        String name = actor.toString();
        map.removeActor(actor);
        return  name + " has disappeared...";
    }

    @Override
    public String menuDescription(Actor actor)
    {
        return "Quit " + actor + "'s Game";
    }
}
