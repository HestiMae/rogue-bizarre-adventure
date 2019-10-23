package edu.monash.fit2099.interfaces;

import edu.monash.fit2099.engine.Skills;
import game.Behaviour;
import game.Sellable;

/**
 * This interface provides the ability to add methods to Actor, without modifying code in the engine,
 * or downcasting references in the game.   
 */

public interface ActorInterface {
    /**
     * Checks if a target is healthy, i.e. at full health
     * @return true if full, false if not
     */
    Boolean isHealthy();

    int getHP();

    void addBehaviour(Behaviour behaviour);

    boolean hasBehaviour(Behaviour behaviour);

    int moveSpeed();
}
