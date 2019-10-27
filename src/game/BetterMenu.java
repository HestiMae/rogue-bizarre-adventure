package game;

import edu.monash.fit2099.engine.*;
import edu.monash.fit2099.engine.Menu;

import java.util.*;
import java.util.function.Predicate;

/**
 * A simple improvement on the base menu class. Categorises actions for the player.
 */
public class BetterMenu extends Menu
{
    static class ActionCategory
    {
        Actor actor;
        String name;
        Predicate<Action> included;
        List<Action> actions = new ArrayList<>();
        ActionCategory(Actor actor, String name, Predicate<Action> included){this.actor = actor; this.name = name; this.included = included;}
        String getName(){return actions.size() == 1 ? actions.get(0).menuDescription(actor) : name + "(" + actions.size() + ")";}
    }

    @Override
    public Action showMenu(Actor actor, Actions actions, Display display)
    {
        List<ActionCategory> actionCategories = new ArrayList<>();

        actionCategories.add(new ActionCategory(actor, "Quit Actions",
                a -> a instanceof RemoveActorAction));
        actionCategories.add(new ActionCategory(actor, "Move Actions",
                a -> a instanceof MoveActorAction));
        actionCategories.add(new ActionCategory(actor, "Attack Actions",
                a -> a instanceof AttackAction || a instanceof RadialAttackAction));
        actionCategories.add(new ActionCategory(actor, "Quest Actions",
                a -> a instanceof AcceptQuest || a instanceof RewardAction || a instanceof EndGameBehaviour));
        actionCategories.add(new ActionCategory(actor, "Shop Actions",
                a -> a instanceof BuyAction || a instanceof SellAction));
        actionCategories.add(new ActionCategory(actor, "Build Actions",
                a -> a instanceof BuildAction));
        actionCategories.add(new ActionCategory(actor, "Other Actions",
                a -> true));

        actions.add(new RemoveActorAction()); // Add quit action

        for (Action action : actions)
        {
             for(ActionCategory cat : actionCategories)
            {
                if (cat.included.test(action))
                {
                    cat.actions.add(action);
                    break; // Next Action
                }
            }
        }

        Action outAction = null;
        while (outAction == null)
        {
            ActionCategory cat = Util.displayListPicker(display, actionCategories, ActionCategory::getName, ac -> "", actionCategory -> !actionCategory.actions.isEmpty(),  false);
            if (cat.actions.size() == 1)
            {
                outAction = cat.actions.get(0);
            }
            else
            {
                outAction = Util.displayListPicker(display, cat.actions, a -> a.menuDescription(actor), Action::hotkey, action ->  true, true);
            }
        }

        return outAction;
    }

    /*private Action sortActions(Actor actor, Actions actions, Display display)
    {
        ArrayList<Character> freeChars = new ArrayList<>();
        HashMap<Character, Action> keyToActionMap = new HashMap<>();
        for (char i = 'a'; i <= 'z'; i++)
            freeChars.add(i);

        // Show with the actions with hotkeys first;
        for (Action action : actions.sorted(new SortHotkeysFirst()))
        {
            String hotKey = action.hotkey();
            char c;
            if (hotKey == null || hotKey.equals(""))
            {
                if (freeChars.isEmpty())
                    break; // we've run out of characters to pick from.
                c = freeChars.get(0);
            } else
            {
                c = hotKey.charAt(0);
            }
            freeChars.remove(Character.valueOf(c));
            keyToActionMap.put(c, action);
            display.println(c + ": " + action.menuDescription(actor));
        }
        char quitChar = '.';
        keyToActionMap.put(quitChar, new RemoveActorAction());
        display.println(quitChar + ": " + "quit game");

        char key;
        do
        {
            key = display.readChar();
        } while (!keyToActionMap.containsKey(key));

        return keyToActionMap.get(key);
    }*/


}
