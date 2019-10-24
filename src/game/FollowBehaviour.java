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
    private List<Actor> targets = new ArrayList<>();
    private Predicate<ActorType> performsBehaviour;
    private ActorType performingActor;
    private BiPredicate<ActorType, Ground> groundIsTarget;
    private BiPredicate<ActorType, Actor> actorIsTarget;
    private BiPredicate<ActorType, Item> itemIsTarget;

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
                return Util.singleSearchAlgorithm(this::returnAction, performingActor, map, this::hasTarget, map.getXRange().max(), true);
            } else
            {
                return new ApproachAction(map.locationOf(
                        Collections.min(targets, Comparator.comparing(
                                target -> Util.distance(map.locationOf(actor), map.locationOf(target))
                        ))), actor.moveTwo());
            }
        }
        return null;
    }

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

    private List<Action> returnAction(Actor actor, Location location)
    {
        return Collections.singletonList(new ApproachAction(location, actor.moveTwo()));
    }
}