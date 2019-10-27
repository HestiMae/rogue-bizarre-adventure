package game;

import java.lang.Math;
import java.util.Random;

import edu.monash.fit2099.engine.Actions;
import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.Ground;
import edu.monash.fit2099.engine.Location;

/**
 * A class that represents bare dirt.
 */
public class Dirt extends Ground {

	private static final double GROWTH_CHANCE = 0.003;
	private Random rand;

	/**
	 * Constructor for dirt.
	 */
	public Dirt() {
		super('.');
		rand = new Random();
		this.addSkill(PassableTerrain.LAND);
	}

	/**
	 * Tick method for Dirt class, gives dirt objects a chance to grow into grass each tick
	 * @param location The location of the Ground
	 */
	@Override
	public void tick(Location location) {
		super.tick(location);

		if (rand.nextFloat() < GROWTH_CHANCE)
		{
			location.setGround(new Grass());
		}
	}

	@Override
	public Actions allowableActions(Actor actor, Location location, String direction)
	{
		Actions actions = new Actions();
		actions.add(new BuildAction(location, direction, new Wall())); //can build walls on Dirt
		actions.add(new BuildAction(location, direction, new Floor())); //can build floors on Dirt
		actions.add(new BuildAction(location, direction, new SelectiveWall<>(dinosaur -> dinosaur.diet.contains(FoodType.PLANT), false, Dinosaur.class, "Herbivore Fence")));
		return actions;
	}

	@Override
	public String getName()
	{
		return "Dirt";
	}

	@Override
	public boolean canActorEnter(Actor actor)
	{
		return actor.hasSkill(PassableTerrain.LAND);
	}
}

