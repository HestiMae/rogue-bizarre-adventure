package game;

import edu.monash.fit2099.engine.*;

/**
 * A kind of quest completed by performing a certain action
 */
public class ActionQuestBehaviour implements Behaviour
{
    private Action lastAction;
    private Action goalAction;
    private Item rewardItem;
    private int rewardMoney;
    private String questName;
    private String description;

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
        return (lastAction.getClass().equals(goalAction.getClass()))? new RewardAction(rewardItem, rewardMoney, questName) : null; //If the desired action has been completed - reward the actor.
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
}
