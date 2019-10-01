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
     *  @param name the name of this copyItem
     * @param type the type of dinosaur this egg will hatch into
     * @param hatchTime the amount of time the egg will take to hatch
     */
    public Egg(String name, Dinosaur type, int hatchTime)
    {
        super("egg", DISPLAY_CHAR, PORTABLE);
        this.type = type;
        this.hatchTime = hatchTime;
        this.age = 0;
    }

    public Egg(Dinosaur type)
    {
        super(type.dinoType() + " Egg", DISPLAY_CHAR, PORTABLE);
        this.type = type;
        this.hatchTime = type.getHatchTime();
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
            currentLocation.addActor(hatch());
            currentLocation.removeItem(this);
        }
    }

    private Dinosaur hatch()
    {
        return type;
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

    @Override
    public Item copyItem()
    {
        return new Egg(this.name, type.copyDinosaur(), this.hatchTime);
    }
}
