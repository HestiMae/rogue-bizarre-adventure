package game;

import edu.monash.fit2099.engine.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class NPC extends Actor
{
    private List<Behaviour> behaviours = new ArrayList<>();
    private List<ActionQuestBehaviour> quests = new ArrayList<>();
    private boolean moveTwo;

    /**
     * Constructor.
     *
     * @param name        the name of the Actor
     * @param displayChar the character that will represent the Actor in the display
     * @param hitPoints   the Actor's starting hit points
     */
    public NPC(String name, char displayChar, int hitPoints, boolean moveTwo)
    {
        super(name, displayChar, hitPoints);
        behaviours.add(new RangedBehaviour());
        behaviours.add(new FollowBehaviour<>(this, npc -> true, (npc, ground) -> false, NPC::canAttack, ((npc, item) -> false)));
        behaviours.add(new WanderBehaviour());
        this.addSkill(PassableTerrain.LAND);
        this.moveTwo = moveTwo;
    }

    @Override
    public Action playTurn(Actions actions, Action lastAction, GameMap map, Display display)
    {
        for (Behaviour behaviour : behaviours)
        {
            Action outAction = behaviour.getAction(this, map);
            if (outAction != null)
            {
                return outAction;
            }
        }
        return null;
    }

    @Override
    public Boolean isHealthy()
    {
        return this.hitPoints == maxHitPoints;
    }

    @Override
    public int getHP()
    {
        return this.hitPoints;
    }

    @Override
    public void addBehaviour(Behaviour behaviour)
    {
        behaviours.add(behaviour);
    }

    @Override
    public boolean hasBehaviour(Behaviour behaviour)
    {
        return behaviours.stream().anyMatch(behaviour1 -> behaviour.getClass().equals(behaviour1.getClass()));
    }

    @Override
    public boolean moveTwo()
    {
        return this.moveTwo;
    }

    @Override
    public boolean canAttack(Actor actor)
    {
        return actor instanceof Enemy && !actor.getClass().isAssignableFrom(this.getClass());

    }

    @Override
    public List<Behaviour> getBehaviours()
    {
        return Collections.unmodifiableList(this.behaviours);
    }

    @Override
    public Actions getAllowableActions(Actor otherActor, String direction, GameMap map) //offers quests to nearby actors if they don't have them already
    {
        Actions actions = new Actions();
        for (ActionQuestBehaviour aqb : quests)
        {
            if (otherActor.getBehaviours().stream()
                    .filter(behaviour -> behaviour instanceof ActionQuestBehaviour)
                    .map(behaviour -> ((ActionQuestBehaviour) behaviour).getGoalAction())
                    .noneMatch(action -> action.menuDescription(otherActor).equals(aqb.getGoalAction().menuDescription(otherActor))))
            {
                actions.add(new AcceptQuest(aqb));
            }
        }

        return actions;
    }

    public void setQuest(ActionQuestBehaviour aqb)
    {
        quests.add(aqb);
    } //sets the quest for this NPC
}
