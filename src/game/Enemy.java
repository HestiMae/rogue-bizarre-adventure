package game;

import edu.monash.fit2099.engine.Actor;

import java.util.ArrayList;
import java.util.List;

public abstract class Enemy extends Actor
{
    protected List<Behaviour> behaviours;
    protected Actor player;
    /**
     * Constructor.
     *
     * @param name        the name of the Actor
     * @param displayChar the character that will represent the Actor in the display
     * @param hitPoints   the Actor's starting hit points
     */
    public Enemy(String name, char displayChar, int hitPoints, Actor player)
    {
        super(name, displayChar, hitPoints);
        this.player = player;
        behaviours = new ArrayList<>();
        behaviours.add(new EnemyBehaviour(this, player));
        behaviours.add(new WanderBehaviour());
    }

    @Override
    public int getHP()
    {
        return hitPoints;
    }
}
