package game;

import edu.monash.fit2099.engine.*;

/**
 * A kind of quest completed by performing a certain action
 */
public class ActionQuestBehaviour implements Behaviour
{
    private Action lastAction; //the last action performed by the actor
    private Action goalAction; //the goal action to complete the quest
    private Item rewardItem; //the reward item for completion
    private int rewardMoney; //a monetary reward for the quest
    private String questName; //the name of the quest
    private String description; //the description of the quest

    /**
     * Constructor to create a new quest
     * @param goalAction the goal action to complete the quest
     * @param rewardItem the reward item for completion
     * @param rewardMoney a monetary reward for the quest
     * @param questName the name of the quest
     * @param description the description of the quest
     */
    public ActionQuestBehaviour(Action goalAction, Item rewardItem, int rewardMoney, String questName, String description)
    {
        this.goalAction = goalAction;
        this.rewardItem = rewardItem;
        this.rewardMoney = rewardMoney;
        this.questName = questName;
        this.description = description;
    }

    @Override
    public Action getAction(Actor actor, GameMap map)
    {
        return (lastAction.menuDescription(actor).equals(goalAction.menuDescription(actor)))? new RewardAction(rewardItem, rewardMoney, this) : null; //If the desired action has been completed - reward the actor.
    }

    @Override
    public Actions getAllActions(Actor actor, GameMap map)
    {
        return new Actions(getAction(actor, map));
    }

    public void setLastAction(Action lastAction)
    {
        this.lastAction = lastAction; //A method to pull the last action of an actor
    }

    @Override
    public String toString()
    {
        return questName + " " + description;
    }

    public String getQuestName()
    {
        return questName;
    }
}
