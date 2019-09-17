package game;

public interface Edible
{
    int getFoodValue(); //returns the food value of the edible

    FoodType getFoodType(); //returns the food type of an edible (PLANT or MEAT)
}
