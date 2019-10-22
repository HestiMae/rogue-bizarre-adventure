package game;

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
        } else if (age >= hatchTime && !(currentLocation.map().isAnActorAt(currentLocation))) //ensures an actor isn't at the Egg's location
        {
            hatch(currentLocation);
        }
    }

    /**
     * Returns the Dinosaur object used as the type to allow it to be placed on the map
     *
     * @return a Dinosaur object
     */
    private Dinosaur hatch(Location currentLocation)
    {
        if (terrainTypes.stream().anyMatch(t -> currentLocation.getGround().hasSkill(t))
                || currentLocation.getExits().stream().anyMatch(exit -> terrainTypes.stream()
                .anyMatch(t -> currentLocation.getGround().hasSkill(t))))
        {
            return type;
        }
        return null;
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
