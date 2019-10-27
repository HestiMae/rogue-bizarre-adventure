package game;

import edu.monash.fit2099.engine.Actor;

import java.util.function.Predicate;

/**
 * A wall that only lets certain actors through
 * @param <ActorType>
 */
public class SelectiveWall<ActorType extends Actor> extends Wall
{
    private Predicate<ActorType> canActorTypeEnter;
    private boolean canOthersEnter;
    private Class<ActorType> actorTypeClass;
    private String descriptor;

    public SelectiveWall(Predicate<ActorType> canActorTypeEnter, boolean canOthersEnter, Class<ActorType> actorTypeClass, String descriptor)
    {
        this.canActorTypeEnter = canActorTypeEnter;
        this.canOthersEnter = canOthersEnter;
        this.actorTypeClass = actorTypeClass;
        this.descriptor = descriptor;
    }

    @Override
    public boolean canActorEnter(Actor actor)
    {
        return (actorTypeClass.isInstance(actor) && canActorTypeEnter.test((ActorType) actor))
                || (!actorTypeClass.isInstance(actor) && canOthersEnter);
    }

    @Override
    public String getName()
    {
        return descriptor;
    }
}
