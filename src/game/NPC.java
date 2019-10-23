package game;

import edu.monash.fit2099.engine.*;

import java.util.ArrayList;
import java.util.List;

public class NPC extends Actor
{
    private List<Behaviour> behaviours = new ArrayList<>();
    private List<ActionQuestBehaviour> quests = new ArrayList<>();
    private int moveSpeed;
    /**
     * Constructor.
     *
     * @param name        the name of the Actor
     * @param displayChar the character that will represent the Actor in the display
     * @param hitPoints   the Actor's starting hit points
     * @param standName   the NPC's stand
     * @param standDisplayChar the display character for their stand
     * @param damage the damage their stand will deal
     * @param attackVerb the verb for their stand attack (e.g. ora ora ora)
     */
    public NPC(String name, char displayChar, int hitPoints, int moveSpeed, String standName, char standDisplayChar, int damage, String attackVerb, int standValue, int weaponRange, WeaponType type)
    {
        super(name, displayChar, hitPoints);
        behaviours.add(new FollowBehaviour<>(this, npc -> true, (npc, ground) -> false, NPC::canAttack, ((npc, item) -> false)));
        behaviours.add(new WanderBehaviour());
        this.addItemToInventory(new Stand(standName, standDisplayChar, damage, attackVerb, standValue, weaponRange, type));
        this.addSkill(PassableTerrain.LAND);
        this.moveSpeed = moveSpeed;
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
    public int moveSpeed()
    {
        return moveSpeed;
    }

    boolean canAttack(Actor target)
    {
        return target instanceof Enemy;
    }

    @Override
    public Actions getAllowableActions(Actor otherActor, String direction, GameMap map)
    {
        Actions actions = new Actions();
        for (ActionQuestBehaviour aqb : quests)
        {
            if (!otherActor.hasBehaviour(aqb))
            {
                actions.add(new AcceptQuest(aqb));
            }
        }

        return actions;
    }

    public void addQuest(ActionQuestBehaviour aqb)
    {
        quests.add(aqb);
    }
}
