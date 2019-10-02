package edu.monash.fit2099.interfaces;

/**
 * This interface provides the ability to add methods to Ground, without modifying code in the engine,
 * or downcasting references in the game.   
 */

public interface GroundInterface {
    /**
     * gets the type of ground as a String - e.g. will return "Dirt" for Dirt
     * @return the type of ground as a String
     */
    String getName();
}
