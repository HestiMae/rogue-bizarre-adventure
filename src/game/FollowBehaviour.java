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
    private List<Actor> targets = new ArrayList<>(); //targets to follow
    private ActorType performingActor; //the actor performing the behaviour
    private Predicate<ActorType> performsBehaviour; //whether the performing actor can perform this behaviour
    private BiPredicate<ActorType, Ground> groundIsTarget; //checks if a ground tile is one the actor is looking for
    private BiPredicate<ActorType, Actor> actorIsTarget; //checks if an actor is one this actor is looking for
    private BiPredicate<ActorType, Item> itemIsTarget; //checks if an item is one the actor is looking for

    /**
     * Constructor.
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

    /**
     * Constructor that takes predicates.
     * @param performingActor the actor performing this behaviour
     * @param performsBehaviour a check on whether they can perform this (for example, dinosaurs must be hungry to do this)
     * @param groundIsTarget a check whether this actor wants to look for ground tiles (false for not at all)
     * @param actorIsTarget a check whether this actor wants to look for actors (false for not at all)
     * @param itemIsTarget a check whether this actor wants to look for items (false for not at all)
     */
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
        if (performsBehaviour.test(performingActor)) //checks whether the actor can do this
        {
            if (targets.isEmpty())
            {
                //Uses the searching algorithm to find target locations based on predicates given. Returns a move towards the first location found
                return Util.singleSearchAlgorithm(this::returnAction, performingActor, map, this::hasTarget, map.getXRange().max());
            } else
            {
                return new ApproachAction(map.locationOf(
                        Collections.min(targets, Comparator.comparing(
                                target -> Util.distance(map.locationOf(actor), map.locationOf(target))
                        ))), actor.moveTwo()); //If targets have been provided - finds a move action towards the closest target.
            }
        }
        return null;
    }

    /**
     * Evaluates the predicates provided for this actor and the location found
     * @param actor the actor searching
     * @param location the location found
     * @return true if the location matches the search, false if not.
     */
    private Boolean hasTarget(Actor actor, Location location)
    {
        return groundIsTarget.test(performingActor, location.getGround())
                || actorIsTarget.test(performingActor, location.getActor())
                || location.getItems().stream().anyMatch(item -> itemIsTarget.test(performingActor, item));
    }

    @Override
    public Actions getAllActions(Actor actor, GameMap map)
    {
        return new Actions(getAction(actor, map));
    }

    /**
     * For use in the search algorithm - what to do when a matching location is found.
     * In this case, we return an approach action to take us towards that location.
     * Unfortunately, it has to be a list, a consequence of generalising the search
     * @param actor the actor searching
     * @param location the location that has been found
     * @return a List of Actions, (only contains one)
     */
    private List<Action> returnAction(Actor actor, Location location)
    {
        return Collections.singletonList(new ApproachAction(location, actor.moveTwo()));
    }
}