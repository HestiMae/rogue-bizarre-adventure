package game;

import java.util.Random;

import edu.monash.fit2099.engine.*;

/**
 * Special Action for attacking other Actors.
 */
public class AttackAction extends Action {

	/**
	 * The Actor that is to be attacked
	 */
	protected Actor target;
	private GameMap map;
	/**
	 * Random number generator
	 */
	private Random rand = new Random();

	private Weapon weapon;

	/**
	 * Constructor.
	 * 
	 * @param target the Actor to attack
	 */
	public AttackAction(Actor target, GameMap map) {
		this.target = target;
		this.map = map;
	}

	public AttackAction(Actor target, Weapon weapon, GameMap map)
	{
		this.target = target;
		this.weapon = weapon;
		this.map = map;
	}

	@Override
	public String execute(Actor actor, GameMap map) {

		//assigns a weapon if it wasn't in the constructor
		if (weapon == null)
		{
			this.weapon = actor.getWeapon();
		}

		if (rand.nextInt(10) < 3) {
			return actor + " misses " + target + ".";
		}

		int damage = weapon.damage();
		target.hurt(damage);

		String result = actor + " " + weapon.verb() + " " + target + " for " + damage + " damage.\n" + target + " health: " + target.getHP();
		if (!target.isConscious()) {
			Item corpse = new Corpse("dead " + target, target);
			map.locationOf(target).addItem(corpse);
			
			Actions dropActions = new Actions();
			for (Item item : target.getInventory())
				dropActions.add(item.getDropAction());
			for (Action drop : dropActions)		
				drop.execute(target, map);
			map.removeActor(target);	
			
			result += System.lineSeparator() + target + " is killed.";
		}

		return result;
	}

	@Override
	public String menuDescription(Actor actor) {
		return actor + " attacks " + target + " with " + weapon + ((Util.distance(map.locationOf(actor), map.locationOf(target)) > 1)? " (ranged)" : " (melee)");
	}
}
