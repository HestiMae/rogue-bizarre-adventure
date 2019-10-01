package edu.monash.fit2099.demo.mars;

import edu.monash.fit2099.engine.Actions;
import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.Ground;
import edu.monash.fit2099.engine.Location;
import game.BuildAction;
import game.Dirt;


public class Floor extends Ground {

	public Floor() {
		super('.');
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
		return null;
	}
}
