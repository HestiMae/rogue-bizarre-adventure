package game;

import edu.monash.fit2099.engine.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public abstract class Enemy extends Actor implements Edible
{
    List<Behaviour> behaviours;
    protected Actor player; //the currrent player actor
    private boolean moveTwo; //can this enemy move 2 spaces at once
    /**
     * Constructor.
     *
     * @param name        the name of the Actor
     * @param displayChar the character that will represent the Actor in the display
     * @param hitPoints   the Actor's starting hit points
     */
    public Enemy(String name, char displayChar, int hitPoints, Actor player, boolean moveTwo)
    {
        super(name, displayChar, hitPoints);
        this.player = player;
        behaviours = new ArrayList<>();
        behaviours.add(new EnemyBehaviour(this, player));
        behaviours.add(new FollowBehaviour<>(this, enemy -> true, (enemy, ground) -> false, (enemy, actor) -> actor instanceof Player, (enemy, item) -> false));
        behaviours.add(new WanderBehaviour());
        this.moveTwo = moveTwo;
    }

    @Override
    public Action playTurn(Actions actions, Action lastAction, GameMap map, Display display)
    {
        for (Behaviour b : behaviours)
        {
            Action outAction = b.getAction(this, map);
            if (outAction != null)
            {
                return outAction;
            }
        }
        return new DoNothingAction();
    }

    @Override
    public int getHP()
    {
        return hitPoints;
    }

    @Override
    public void addBehaviour(Behaviour behaviour)
    {
        behaviours.add(behaviour);
    }

    @Override
    public boolean hasBehaviour(Behaviour behaviour)
    {
        return behaviours.stream().anyMatch(behaviour1 -> behaviour.getClass().equals(behaviour1.getClass()));
    }

    @Override
    public boolean moveTwo()
    {
        return moveTwo;
    }

    @Override
    public boolean canAttack(Actor actor)
    {
        //Enemies will attack NPCs, the player, and dinosaurs
        return (actor instanceof Player || actor instanceof NPC || actor instanceof Dinosaur) && !actor.getClass().isAssignableFrom(this.getClass());
    }

    @Override
    public List<Behaviour> getBehaviours()
    {
        return Collections.unmodifiableList(behaviours);
    }

    @Override
    public Actions getAllowableActions(Actor otherActor, String direction, GameMap map)
    {
        Actions actions = new Actions();
        actions.add(new AttackAction(this, map));
        return actions;
    }

    @Override
    public int getFoodValue()
    {
        return 100;
    }

    @Override
    public FoodType getFoodType()
    {
        return FoodType.MEAT; //enemies are also hunted by dinosaurs
    }
}
