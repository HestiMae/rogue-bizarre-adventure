package game;

import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.Exit;
import edu.monash.fit2099.engine.Item;
import edu.monash.fit2099.engine.Location;

import java.util.List;


/**
 * Class representing Eggs in game
 */
public class Egg extends Item implements Edible, Sellable
{
    private Dinosaur type; //the type of Dinosaur
    private int hatchTime; //the time the Egg will take to hatch
    private int age; //the current age in turns of the Egg
    private List<PassableTerrain> terrainTypes; //the type of terrain the egg is supposed to be on
    private static final boolean PORTABLE = true; //Eggs can be picked up
    private static char DISPLAY_CHAR = 'o'; //display character for eggs


    /**
     * Simple constructor taking only a type as all other parameters are shared between instances
     *
     * @param type the type of Dinosaur
     */
    public Egg(Dinosaur type)
    {
        super(type.dinoType() + " Egg", DISPLAY_CHAR, PORTABLE);
        this.type = type;
        this.terrainTypes = type.getEggTerrain();
        this.hatchTime = type.getHatchTime();
        this.age = 0;
    }

    @Override
    public void tick(Location currentLocation)
    {
        super.tick(currentLocation);
        age++;
        if (age == hatchTime / 2)
        {
            displayChar = 'O';
        } else if (age >= hatchTime)
        {

            hatch(currentLocation);
        }
    }

    /**
     * Allow the egg to age even when being carried
     *
     * @param currentLocation The location of the actor carrying this item.
     * @param actor           The actor carrying this item.
     */
    @Override
    public void tick(Location currentLocation, Actor actor)
    {
        age++;
        if (age == hatchTime / 2)
        {
            displayChar = 'O';
        }
    }

    /**
     * Places the Dinosaur object type on the map
     */
    private void hatch(Location hatchLocation)
    {
        if (!hatchLocation.containsAnActor() && terrainTypes.stream().anyMatch(t -> hatchLocation.getGround().hasSkill(t)))
        {
            hatchLocation.addActor(this.type);
            hatchLocation.removeItem(this);
        }
        else if (hatchLocation.getExits().stream().anyMatch(exit -> terrainTypes.stream()
                .anyMatch(t -> exit.getDestination().getGround().hasSkill(t))))
        {
            //if the currentlocation isn't of the right terrain, checks if the exits are, grabs the first one that lets us place an actor there.
            hatchLocation
                    .getExits().stream().map(Exit::getDestination)
                    .filter(location -> !location.containsAnActor() && terrainTypes.stream().anyMatch(t -> location.getGround().hasSkill(t)))
                    .findFirst().ifPresent(altLocation -> {
                        altLocation.addActor(this.type);
                        altLocation.removeItem(this);
            });
        }
    }

    @Override
    public int getFoodValue()
    {
        return 20;
    }

    @Override
    public FoodType getFoodType()
    {
        return FoodType.MEAT;
    }

    @Override
    public int getValue()
    {
        return type.getEggValue();
    }

    @Override
    public Item copyItem()
    {
        return new Egg(type.copyDinosaur());
    }
}
