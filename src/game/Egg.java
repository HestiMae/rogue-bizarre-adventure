package game;

import edu.monash.fit2099.engine.Item;
import edu.monash.fit2099.engine.Location;


public class Egg extends Item implements Edible, Sellable
{
    private Dinosaur type;
    private int hatchTime;
    private int age;
    private static final boolean PORTABLE = true;
    private static char DISPLAY_CHAR = 'o';

    /***
     * Constructor.
     *  @param name the name of this Item
     * @param type the type of dinosaur this egg will hatch into
     * @param hatchTime the amount of time the egg will take to hatch
     */
    public Egg(String name, Dinosaur type, int hatchTime)
    {
        super(name, DISPLAY_CHAR, PORTABLE);
        this.type = type;
        this.hatchTime = hatchTime;
        this.age = 0;
    }

    @Override
    public void tick(Location currentLocation)
    {
        super.tick(currentLocation);
        age++;
        if (age == hatchTime/2)
        {
            displayChar = 'O';
        }
        else if (age == hatchTime)
        {
            hatch();
        }
    }

    private Dinosaur hatch()
    {
       //is there a better way to do this without checking what type of Dinosaur we have..?
        if (type instanceof Protoceratops)
        {
            return new Protoceratops("name");
        }
        else if (type instanceof Velociraptor)
        {
            return new Velociraptor("name");
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
        return 10;
    }
}
