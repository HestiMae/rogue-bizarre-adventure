package game;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.GameMap;
import edu.monash.fit2099.engine.Item;

public class RewardAction extends Action
{
    private Item rewardItem;
    private int rewardMoney;
    private String questName;

    public RewardAction(Item rewardItem, int rewardMoney, String questName)
    {
        this.rewardItem = rewardItem;
        this.rewardMoney = rewardMoney;
        this.questName = questName;
    }

    @Override
    public String execute(Actor actor, GameMap map)
    {
        actor.getInventory().add(rewardItem);
        ((Player) actor).addMoney(rewardMoney);
        return menuDescription(actor);
    }

    @Override
    public String menuDescription(Actor actor)
    {
        return "Congratulations! You've completed " + questName + "! You've been awarded " + rewardItem.toString()
                + " and earned $" + rewardMoney + ".";
    }
}
