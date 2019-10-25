package game;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.GameMap;
import edu.monash.fit2099.engine.Item;

public class RewardAction extends Action
{
    private Item rewardItem; //the item to reward for completion
    private int rewardMoney; //the money to award upon completion
    private ActionQuestBehaviour quest; //the quest to be rewarded for

    /**
     * Constructor.
     * @param rewardItem the item to reward for completion
     * @param rewardMoney the money to award upon completion
     * @param quest the quest to be rewarded for
     */
    public RewardAction(Item rewardItem, int rewardMoney, ActionQuestBehaviour quest)
    {
        this.rewardItem = rewardItem;
        this.rewardMoney = rewardMoney;
        this.quest = quest;
    }

    @Override
    public String execute(Actor actor, GameMap map)
    {
        actor.addItemToInventory(rewardItem);
        ((Player) actor).addMoney(rewardMoney);
        ((Player) actor).removeBehaviour(quest); //removes the quest from the actors behaviour list
        return menuDescription(actor);
    }

    @Override
    public String menuDescription(Actor actor)
    {
        return "Congratulations! You've completed " + quest.getQuestName() + "! You've been awarded " + rewardItem.toString()
                + " and earned $" + rewardMoney + ".";
    }

    public ActionQuestBehaviour getQuest()
    {
        return quest;
    }
}
