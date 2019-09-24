package game;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Item;

public class DinosaurTag extends Item implements Sellable
{

    /**
     * Constructor for dinosaur tag used to sell dinosaurs
     *
     */
    public DinosaurTag()
    {
        super("Dinosaur tag", '*', true);
    }

    /**
     * Adds an allowable action associated with the dinosaur tag
     * @param action action to add to the dinosaur tag
     */
    public void addAction(Action action)
    {
        this.allowableActions.add(action);
    }

    @Override
    public int getValue()
    {
        return 0;
    }
}
