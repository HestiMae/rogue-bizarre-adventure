package game;

import edu.monash.fit2099.engine.*;

import java.util.*;
import java.util.function.BiPredicate;
import java.util.function.Predicate;

/**
 * A class that figures out a MoveAction that will move the actor one step
 * closer to a target Actor/Ground/Item.
 */
public class FollowBehaviour<ActorType extends Actor> implements Behaviour
{
    //TODO: Can we search only one kind of terrain..?

    private List<Actor> targets = new ArrayList<>();
    private Predicate<ActorType> performsBehaviour;
    private ActorType performingActor;
    private BiPredicate<ActorType, Ground> groundIsTarget;
    private BiPredicate<ActorType, Actor> actorIsTarget;
    private BiPredicate<ActorType, Item> itemIsTarget;

    /**
     * Constructor.
     *
     * @param targets the Actors to follow
     */
    public FollowBehaviour(ActorType performingActor, Predicate<ActorType> performsBehaviour, List<Actor> targets)
    {
        this.targets = targets;
        this.performsBehaviour = performsBehaviour;
        this.performingActor = performingActor;
        this.groundIsTarget = (x, y) -> false;
        this.actorIsTarget = (x, y) -> false;
        this.itemIsTarget = (x, y) -> false;

    }

    public FollowBehaviour(ActorType performingActor, Predicate<ActorType> performsBehaviour, BiPredicate<ActorType, Ground> groundIsTarget, BiPredicate<ActorType, Actor> actorIsTarget, BiPredicate<ActorType, Item> itemIsTarget)
    {
        this.performsBehaviour = performsBehaviour;
        this.performingActor = performingActor;
        this.groundIsTarget = groundIsTarget;
        this.actorIsTarget = actorIsTarget;
        this.itemIsTarget = itemIsTarget;

    }

    @Override
    public Action getAction(Actor actor, GameMap map)
    {
        if (performsBehaviour.test(performingActor))
        {
            if (targets.isEmpty())
            {
                Location startPoint = map.locationOf(actor);
                Set<Location> visitedLocations = new HashSet<>();
                Set<Location> locationsToVisit = new HashSet<>();
                locationsToVisit.add(startPoint);

                while (!visitedLocations.containsAll(locationsToVisit)) //loops until all locations to check have been checked
                {
                    for (Location location : new HashSet<>(locationsToVisit))
                    {
                        for (Exit exit : location.getExits())
                        {
                            locationsToVisit.add(exit.getDestination()); //adds each exit from the locations to check to the list of locations to check
                        }
                        if (hasTarget(location)) //checks if the current location has an edible
                        {
                            return new ApproachAction(location); //moves the dinosaur towards the edible
                        }
                        visitedLocations.add(location); //adds the location to the list of checked locations
                    }
                    locationsToVisit.removeAll(visitedLocations); //removes locations already checked/"visited"
                }
            } else
            {
                return new ApproachAction(map.locationOf(
                        Collections.min(targets, Comparator.comparing(
                                target -> Util.distance(map.locationOf(actor), map.locationOf(target))
                        ))));
            }
        }
        return null;
    }

    private Boolean hasTarget(Location location)
    {
        return groundIsTarget.test(performingActor, location.getGround())
                || actorIsTarget.test(performingActor, location.getActor())
                || location.getItems().stream().anyMatch(item -> itemIsTarget.test(performingActor, item));
    }
}