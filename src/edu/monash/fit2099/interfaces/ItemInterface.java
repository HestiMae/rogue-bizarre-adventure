package edu.monash.fit2099.interfaces;

import edu.monash.fit2099.engine.Item;

/**
 * This interface provides the ability to add methods to copyItem, without modifying code in the engine,
 * or downcasting references in the game.   
 */
public interface ItemInterface {

    /**
     * Simple "copy constructor" to avoid issues of multiple references to the same Item (used in shop).
     * Decided to put it here instead of {@see Sellable} as we may need to duplicate items for other purposes later.
     * @return a copy of the item
     */
    Item copyItem();

}
