package game;

import edu.monash.fit2099.engine.*;


/**
 * Behaviour to allow carnivores to hunt other dinosaurs.
 */
public class HuntBehaviour implements Behaviour
{
    private Dinosaur dino; //The hunting dinosaur

    /**
     * Simple constructor
     *
     * @param dino the hunter dinosaur
     */
    public HuntBehaviour(Dinosaur dino)
    {
        this.dino = dino;
    }

    @Override
    public Action getAction(Actor actor, GameMap map)
    {
        Location startPoint = map.locationOf(dino);
        for (Exit exit : startPoint.getExits())
        {
            if (dino.canHunt(exit.getDestination().getActor()))
            {
                return new AttackAction(exit.getDestination().getActor()); //returns an attack action once the hunter is next to them
            }
        }
        return null;
    }

    @Override
    public Actions getAllActions(Actor actor, GameMap map)
    {
        return new Actions(getAction(actor, map));
    }
}