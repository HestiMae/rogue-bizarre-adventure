package game;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actions;
import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.Display;
import edu.monash.fit2099.engine.GameMap;
import edu.monash.fit2099.engine.Menu;

/**
 * Class representing the Player.
 */
public class Player extends Actor
{

	private Menu menu = new Menu();
	private int wallet = 0;

	/**
	 * Constructor.
	 *
	 * @param name        Name to call the player in the UI
	 * @param displayChar Character to represent the player in the UI
	 * @param hitPoints   Player's starting number of hitpoints
	 */
	public Player(String name, char displayChar, int hitPoints)
	{
		super(name, displayChar, hitPoints);
	}

	@Override
	public Action playTurn(Actions actions, Action lastAction, GameMap map, Display display)
	{
		// Handle multi-turn Actions
		if (lastAction.getNextAction() != null)
			return lastAction.getNextAction();
		return menu.showMenu(this, actions, display);
	}

	public void addMoney(int value)
	{
		this.wallet += value;
	}

	public String removeMoney(int value)
	{
		if (value < this.wallet) {
			return "Not enough money to complete purchase";
		}

		this.wallet -= value;
		return null;
	}

	public int getWallet() {return this.wallet;}

	@Override
	public Boolean isHealthy()
	{
		if (this.hitPoints < this.maxHitPoints) {
			return false;
		}
		return true;
	}
}
