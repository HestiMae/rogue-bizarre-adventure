package game;

import edu.monash.fit2099.engine.*;

import java.util.List;

/**
 * Behaviour allowing Dinosaurs to search for suitable food in the map.
 */
public class EatBehaviour extends Action implements Behaviour
{
    private Dinosaur dino; //the Dinosaur doing the eating
    private List<FoodType> diet; //the diet of the Dinosaur

    /**
     * Constructor
     *
     * @param dino the Dinosaur doing the eating
     * @param diet the diet of the Dinosaur
     */
    public EatBehaviour(Dinosaur dino, List<FoodType> diet)
    {
        this.dino = dino;
        this.diet = diet;
    }

    @Override
    public Action getAction(Actor actor, GameMap map)
    {
        if (hasEdible(map.locationOf(actor))) //checks if there is an edible at the Dinosaurs location that is in their diet
        {
            return this;
        }
        return null;
    }

    @Override
    public String execute(Actor actor, GameMap map)
    {
        Location here = map.locationOf(dino);
        if (dino.canEat(here.getGround())) //if the ground is an edible, do this
        {
            Ground ground = here.getGround();
            dino.eat(((Edible) here.getGround()));
            here.setGround(new Dirt()); //ground is set to dirt once eaten
            return dino + " eats " + ground.getName() + " at " + Util.locationString(here)
                    + " Dino hunger: " + dino.foodLevel;
        } else
        {
            for (Item item : here.getItems())
            {
                if (dino.canEat(item)) //finds the edible item from the location's list
                {
                    dino.eat(((Edible) item));
                    String name = item.toString();
                    here.removeItem(item);
                    return dino + " eats " + name + " at " + Util.locationString(here)
                            + " Dino hunger: " + dino.foodLevel;
                }
            }
        }
        return dino + " tried to eat something at " + Util.locationString(here);
    }

    /**
     * Checks if a location contains an edible. Finds both Ground and Items
     *
     * @param location the location to check
     * @return true if an edible is found, false if not.
     */
    private boolean hasEdible(Location location)
    {
        //checks if the ground is edible and the food type is in the diet, or if an item is edible and is in the diet.
        return dino.canEat(location.getGround()) ||
                location.getItems().stream().anyMatch(x -> dino.canEat(x));
    }

    @Override
    public String menuDescription(Actor actor)
    {
        return null;
    }
}
