package game;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.GameMap;

/**
 * An action to accept a quest. Adds it to the actors behaviours list
 */
public class AcceptQuest extends Action
{
    ActionQuestBehaviour quest;

    public AcceptQuest(ActionQuestBehaviour quest)
    {
        this.quest = quest;
    }
    @Override
    public String execute(Actor actor, GameMap map)
    {
        actor.addBehaviour(quest);
        return  actor + " accepts the quest " + quest;
    }

    @Override
    public String menuDescription(Actor actor)
    {
        return "Take on the quest: " + quest;
    }
}
