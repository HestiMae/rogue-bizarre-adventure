package edu.monash.fit2099.engine;

import java.util.List;

import edu.monash.fit2099.interfaces.ItemInterface;

/**
 * Abstract base class representing a physical object in the game world.
 *
 */
public abstract class Item implements ItemInterface, Printable, Skilled {

	protected boolean portable;
	protected String name;
	protected char displayChar;
	protected Actions allowableActions;
	protected Skills skills = new Skills();

	/***
	 * Constructor.
	 * 
	 * @param name the name of this item
	 * @param displayChar the character to use to represent this item if it is on the ground
	 * @param portable true if and only if the item can be picked up
	 */
	public Item(String name, char displayChar, boolean portable) {
		this.name = name;
		this.displayChar = displayChar;
		this.portable = portable;
		allowableActions = new Actions();
	}

    /**
     * Inform a carried item of the passage of time.
     * 
     * This method is called once per turn, if the item is being carried.
     * @param currentLocation The location of the actor carrying this item.
     * @param actor The actor carrying this item.
     */
	public void tick(Location currentLocation, Actor actor) {
	}
	
    /**
     * Inform an item on the ground of the passage of time.
     * This method is called once per turn, if the item rests upon the ground.
     * @param currentLocation The location of the ground on which we lie.
     */
	public void tick(Location currentLocation) {
	}

	@Override
	public char getDisplayChar() {
		return displayChar;
	}

	@Override
	public String toString() {
		return name;
	}

	/**
	 * Create and return an action to pick this item up.
	 * If this item is not portable, returns null.
	 * 
	 * @return a new PickUpItemAction if this item is portable, null otherwise.
	 */
	public PickUpItemAction getPickUpAction() {
		if(portable)
			return new PickUpItemAction(this);
		
		return null;
	}

	/**
	 * Create and return an action to drop this item.
	 * If this item is not portable, returns null.
	 * @return a new DropItemAction if this item is portable, null otherwise.
	 */
	public DropItemAction getDropAction() {
		if(portable)
			return new DropItemAction(this);
		
		return null;
	}
	
	/**
	 * Getter.
	 * 
	 * Returns an unmodifiable copy of the actions list so that calling methods won't
	 * be able to change what this item can do without the item checking.
	 * @return an unmodifiable list of Actions
	 */
	public List<Action> getAllowableActions() {
		return allowableActions.getUnmodifiableActionList();
	}


	/**
	 * Casts this item to a Weapon if possible.
	 * 
	 * @return a reference to the current item as type Weapon, or null if this item isn't a Weapon
	 */
	public Weapon asWeapon() {
		return this instanceof Weapon ? (Weapon) this : null;
	}

	/**
	 * Does this item have the given Skill?
	 * 
	 * @return true if and only if is item has the given Skill
	 */
	public boolean hasSkill(Enum<?> skill) {
		return skills.hasSkill(skill);
	}

	/**
	 * Add a Skill to this item.
	 * 
	 * @param skill the Skill to add
	 */
	public void addSkill(Enum<?> skill) {
		skills.addSkill(skill);
	}

	/**
	 * Remove a Skill from this item.
	 * 
	 * @param skill the Skill to remove
	 */
	public void removeSkill(Enum<?> skill) {
		skills.removeSkill(skill);
	}
}
