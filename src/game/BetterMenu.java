package game;

import edu.monash.fit2099.engine.*;
import edu.monash.fit2099.engine.Menu;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;

public class BetterMenu extends Menu
{
    @Override
    public Action showMenu(Actor actor, Actions actions, Display display)
    {
        Actions shopActions = new Actions();
        Actions attackActions = new Actions();
        Actions moveActions = new Actions();
        Actions miscActions = new Actions();
        Actions questActions = new Actions();

        for (Action action : actions)
        {
            if (action instanceof BuyAction || action instanceof SellAction)
            {
                shopActions.add(action);
            } else if (action instanceof AttackAction || action instanceof RadialAttackAction)
            {
                attackActions.add(action);
            } else if (action instanceof MoveActorAction)
            {
                moveActions.add(action);
            } else if (action instanceof AcceptQuest || action instanceof RewardAction)
            {
                questActions.add(action);
            } else
            {
                miscActions.add(action);
            }
        }
        display.println("Select 0 to quit the game"
                + "\nSelect 1 for Move Actions (" + moveActions.size() + ")"
                + "\nSelect 2 for Attack Actions (" + attackActions.size() + ")"
                + "\nSelect 3 for Quest Actions (" + questActions.size() + ")"
                + "\nSelect 4 for Shop Actions (" + shopActions.size() + ")"
                + "\nSelect 5 for Misc Actions (" + miscActions.size() + ")");

        switch (display.readChar())
        {
            case '0':
                display.println("Selected quit game!");
                return new RemoveActorAction();
            case '1':
                display.println("Selected Move Actions");
                return sortActions(actor, moveActions, display);
            case '2':
                display.println("Selected Attack Actions");
                return sortActions(actor, attackActions, display);
            case '3':
                display.println("Selected Quest Actions");
                return sortActions(actor, questActions, display);
            case '4':
                display.println("Selected Shop Actions");
                return sortActions(actor, shopActions, display);
            case '5':
                display.println("Selected Misc Actions");
                return sortActions(actor, miscActions, display);
        }
        return null;
    }

    private Action sortActions(Actor actor, Actions actions, Display display)
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
    }

    static class SortHotkeysFirst implements Comparator<Action>
    {
        public int compare(Action a, Action b)
        {
            if (a.hotkey() != null && b.hotkey() == null)
                return -1;

            if (a.hotkey() == null && b.hotkey() != null)
                return 1;

            return 0;
        }
    }
}
