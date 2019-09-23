package game;

import edu.monash.fit2099.engine.*;

import java.util.HashSet;
import java.util.Set;

public class HuntBehaviour implements Behaviour
{
    private Dinosaur dino;
    private Dinosaur target;

    public HuntBehaviour(Dinosaur dino)
    {
        this.dino = dino;
    }

    @Override
    public Action getAction(Actor actor, GameMap map)
    {
        Location startPoint = map.locationOf(dino);
        Set<Location> visitedLocations = new HashSet<>();
        Set<Location> locationsToVisit = new HashSet<>();
        locationsToVisit.add(startPoint);

        if (!dino.isFull())
        {
            if (!nextToTarget(dino, map))
            {
                while (!visitedLocations.containsAll(locationsToVisit))
                {
                    for (Location location : new HashSet<>(locationsToVisit))
                    {
                        for (Exit exit : location.getExits())
                        {
                            locationsToVisit.add(exit.getDestination());
                        }
                        if (location.getActor() instanceof Dinosaur && dino.diet.contains(((Edible) location.getActor()).getFoodType()))
                        {
                            return Util.moveLogic(startPoint, location, dino);
                        }
                        visitedLocations.add(location);
                    }
                    locationsToVisit.removeAll(visitedLocations);
                }
            }
            else
            {
                return new AttackAction(target);
            }
        }
        return null;
    }

    private boolean nextToTarget(Dinosaur dino, GameMap map)
    {
        Location startPoint = map.locationOf(dino);
        for (Exit exit : startPoint.getExits())
        {
            if (exit.getDestination().getActor() instanceof Dinosaur && dino.diet.contains(((Edible) exit.getDestination().getActor()).getFoodType()))
            {
                this.target = (Dinosaur) exit.getDestination().getActor();
                return true;
            }
        }
        return false;
    }
}