package game;

import edu.monash.fit2099.engine.*;

import java.util.HashSet;
import java.util.Set;

/**
 * Behaviour to allow carnivores to hunt other dinosaurs.
 */
public class HuntBehaviour implements Behaviour
{
    private Dinosaur dino; //The hunting dinosaur
    private Dinosaur target; //the prey

    /**
     * Simple constructor
     * @param dino the hunter dinosaur
     */
    public HuntBehaviour(Dinosaur dino)
    {
        this.dino = dino;
    }

    @Override
    public Action getAction(Actor actor, GameMap map)
    {
        //this is the same search algorithm as in eat action.
        Location startPoint = map.locationOf(dino);
        Set<Location> visitedLocations = new HashSet<>();
        Set<Location> locationsToVisit = new HashSet<>();
        locationsToVisit.add(startPoint);

        if (!dino.isFull())
        {
            if (!nextToTarget(dino, map)) //checks that the dinosaur isn't already next to the target
            {
                while (!visitedLocations.containsAll(locationsToVisit)) //loops as long as there are still locations to check
                {
                    for (Location location : new HashSet<>(locationsToVisit))
                    {
                        for (Exit exit : location.getExits())
                        {
                            locationsToVisit.add(exit.getDestination()); //adds each exit from the list of locations to check to the list of locations to check
                        }
                        if (location.getActor() instanceof Dinosaur
                                && !(dino.getClass().isAssignableFrom(location.getActor().getClass())) && dino.diet.contains(((Edible) location.getActor()).getFoodType())) //ensures the target dinosaur isn't of the same species, and is of the right food type.
                        {
                            return new ApproachAction(startPoint, location); //creates a move action to move the dinosaur towards the target
                        }
                        visitedLocations.add(location); //adds the checked location to the list of checked locations
                    }
                    locationsToVisit.removeAll(visitedLocations); //removes all checked locations from the list of locations to visit.
                }
            }
            else
            {
                return new AttackAction(target); //returns an attack action once the hunter is next to them
            }
        }
        return null;
    }

    /**
     * Checks if the dinosaur is next to the target
     * @param dino the dinosaur
     * @param map the current map
     * @return true if it is next to, false if not.
     */
    private boolean nextToTarget(Dinosaur dino, GameMap map)
    {
        Location startPoint = map.locationOf(dino);
        for (Exit exit : startPoint.getExits())
        {
            if (exit.getDestination().getActor() instanceof Dinosaur && !(dino.getClass().isAssignableFrom(exit.getDestination().getActor().getClass())) && dino.diet.contains(((Edible) exit.getDestination().getActor()).getFoodType()))
            {
                this.target = (Dinosaur) exit.getDestination().getActor();
                return true;
            }
        }
        return false;
    }
}