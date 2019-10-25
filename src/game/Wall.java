package game;

import edu.monash.fit2099.engine.Actions;
import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.Ground;
import edu.monash.fit2099.engine.Location;

/**
 * A simple structure that can be built by the player for protection of themselves or their dinosaurs.
 */
public class Wall extends Ground {

	public Wall() {
		super('#');
	}
	
	@Override
	public boolean canActorEnter(Actor actor) {
		return false;
	}
	
	@Override
	public boolean blocksThrownObjects() {
		return true;
	}

	@Override
	public Actions allowableActions(Actor actor, Location location, String direction)
	{
		Actions actions = new Actions();
		actions.add(new BuildAction(location, direction, new Dirt()));
		return actions;
	}

	@Override
	public String getName()
	{
		return "Wall";
	}
}