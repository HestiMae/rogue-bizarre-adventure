package game;

import edu.monash.fit2099.engine.*;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Class representing the Player.
 */
public class Player extends Actor implements Edible
{

	private Menu menu = new BetterMenu(); //displays the list of options for the player
	private int wallet = 50; //the players wallet. We decided to put it here and require some downcasts instead of giving all actors wallets (that just seems silly, if you ask me)
	private List<Behaviour> behaviours = new ArrayList<>();

	/**
	 * Constructor.
	 * @param name        Name to call the player in the UI
	 * @param displayChar Character to represent the player in the UI
	 * @param hitPoints   Player's starting number of hitpoints
	 */
	public Player(String name, char displayChar, int hitPoints)
	{
		super(name, displayChar, hitPoints);
		addSkill(PassableTerrain.LAND);
		behaviours.add(new RangedBehaviour());
		behaviours.add(new EndGameBehaviour());
		inventory.add(new Stand("Magician's Red", '*', 50, "Crossfire Hurricane", 10000, 20, Arrays.asList(WeaponType.MELEE, WeaponType.RANGED, WeaponType.RADIAL)));
	}

	/**
	 * Returns a collection of the Actions that the otherActor can do to the current Actor.
	 *
	 * @param actions    collection of possible Actions for this Actor
	 * @param lastAction The Action this Actor took last turn. Can do interesting things in conjunction with Action.getNextAction()
	 * @param map        the map containing the Actor
	 * @param display    the I/O object to which messages may be written
	 * @return List of available actions for the turn
	 */
	@Override
	public Action playTurn(Actions actions, Action lastAction, GameMap map, Display display)
	{
		// Handle multi-turn Actions
		if (lastAction.getNextAction() != null)
		{
			return lastAction.getNextAction();
		}
		if (behaviours.stream().anyMatch(behaviour -> behaviour instanceof ActionQuestBehaviour))
		{
			display.println("Active Quests:");
			behaviours.stream().filter(behaviour -> behaviour instanceof ActionQuestBehaviour)
					.forEach(behaviour -> {((ActionQuestBehaviour) behaviour).setLastAction(lastAction);
					display.println(behaviour.toString());
					}); //Gives action based quests the last action completed, and displays the details of any active quests.

		}
		behaviours.stream().map(behaviour -> behaviour.getAllActions(this, map)).filter(Objects::nonNull).forEach(actions::add);
		return menu.showMenu(this, actions, display);
	}

	/**
	 * Adds an amount of money to the player's wallet
	 * @param value amount of money to be added
	 */
	public void addMoney(int value)
	{
		this.wallet += value;
	}

	/**
	 * Removes an amount of money from the player's wallet
	 * @param value amount of money to be removed
	 * @return true if success, false if player does not have enough money to remove
	 */
	public Boolean removeMoney(int value)
	{
		if (value > this.wallet) {
			return false;
		}

		this.wallet -= value;
		return true;
	}

	/**
	 * Gets the amount of money the player currently has
	 * @return current wallet value
	 */
	public int getWallet() {return this.wallet;}


	@Override
	public Boolean isHealthy()
	{
		return this.hitPoints >= this.maxHitPoints;
	}

	@Override
	public int getHP()
	{
		return hitPoints;
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
		return false;
	}

    @Override
    public boolean canAttack(Actor actor)
    {
        return (actor instanceof Enemy || actor instanceof Dinosaur)  && !actor.getClass().isAssignableFrom(this.getClass());
    }

	@Override
	public List<Behaviour> getBehaviours()
	{
		return Collections.unmodifiableList(this.behaviours);
	}

	/**
	 * Required for quest functionality
	 * @param behaviour removes a behaviour from the player list
	 */
	void removeBehaviour(Behaviour behaviour)
	{
		behaviours.remove(behaviour);
	}

	@Override
	public int getFoodValue()
	{
		return 100;
	}

	@Override
	public FoodType getFoodType()
	{
		return FoodType.MEAT; //carnivorous dinos will hunt the player
	}
}
