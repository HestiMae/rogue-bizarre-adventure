package game;

import java.lang.Math;

import edu.monash.fit2099.engine.Actions;
import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.Ground;
import edu.monash.fit2099.engine.Location;

/**
 * A class that represents bare dirt.
 */
public class Dirt extends Ground {

	/**
	 * Constructor for dirt.
	 */
	public Dirt() {
		super('.');
	}

	/**
	 * Tick method for Dirt class, gives dirt objects a chance to grow into grass each tick
	 * @param location The location of the Ground
	 */
	@Override
	public void tick(Location location) {
		super.tick(location);

		double growthChance = 0.003;
		double rand = Math.random();

		if (rand < growthChance)
		{
			location.setGround(new Grass());
		}
	}

	@Override
	public Actions allowableActions(Actor actor, Location location, String direction)
	{
		Actions actions = new Actions();
		actions.add(new BuildAction(location, direction, new Wall()));
		actions.add(new BuildAction(location, direction, new Floor()));
		return actions;
	}

	@Override
	public String getName()
	{
		return "Dirt";
	}
}

