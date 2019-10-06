package game;

import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.Ground;
import edu.monash.fit2099.engine.Location;
import edu.monash.fit2099.engine.Exit;

import java.util.List;
import java.lang.Math;

/**
 * This class represents trees
 */
public class Tree extends Ground implements Edible{
	private int age = 0;

	/**
	 * Constructor for tree .
	 */
	public Tree() {
		super('+');
	}

	/**
	 * Tick method for tree which ages the tree and changes its display character accordingly and provides a chance for
	 * adjacent dirt or grass ground to grow trees each tick.
	 * @param location The location of the Ground
	 */
	@Override
	public void tick(Location location) {
		super.tick(location);

		List<Exit> exits = location.getExits();
		double growthChance = 0.005;

		for (Exit exit: exits)
		{
			Ground groundType = exit.getDestination().getGround();
			// Rule: trees can only grow on adjacent grass or dirt ground types
			if (groundType instanceof Dirt || groundType instanceof Grass)
			{
				double rand = Math.random();
				if (rand < growthChance)
				{
					exit.getDestination().setGround(new Tree());
				}
			}
		}

		age++;
		if (age == 10)
			displayChar = 't';
		if (age == 20)
			displayChar = 'T';
	}

	/**
	 * Gets the number of metabolise points restored when eaten
	 * @return metabolise points restored when eaten
	 */
	@Override
	public int getFoodValue()
	{
		return 10;
	}

	/**
	 * Gets the type of food that tree is
	 * @return enum food type of tree
	 */
	@Override
	public FoodType getFoodType()
	{
		return FoodType.PLANT;
	}

	@Override
	public String getName()
	{
		return "Tree";
	}

	@Override
	public boolean canActorEnter(Actor actor)
	{
		return actor.hasSkill(PassableTerrain.LAND);
	}
}
