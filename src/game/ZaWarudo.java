package game;

import edu.monash.fit2099.engine.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Modified version of World. The name is just a reference to the anime JoJos Bizarre Adventure.
 */
public class ZaWarudo extends World
{
    //TODO: End game options.
    private static final int DIO_RATE = 40;
    private int turnCounter = 0;
    private List<Enemy> enemies = new ArrayList<>();

    /**
     * Constructor.
     *
     * @param display the Display that will display this World.
     */
    public ZaWarudo(Display display)
    {
        super(display);
    }

    /**
     * {@inheritDoc}
     * A slightly modified version of run that displays a welcome message with an option to start or quit.
     */
    @Override
    public void run()
    {
        if (player == null)
            throw new IllegalStateException();

        // initialize the last action map to nothing actions;
        for (Actor actor : actorLocations)
        {
            lastActionMap.put(actor, new DoNothingAction());
        }

        display.println("Welcome to Egypt! The Stardust Crusaders are taking a break from fighting DIO's Stand Users to raise some Dinosaurs in these filler episodes." +
                "\nBegin with a small herd of Protoceratops and expand your dino empire!" +
                "\nBut be careful... Lord DIO is watching..." +
                "\nPlease type b to begin, or q to quit.");


        // This loop is basically the whole game
        if (display.readChar() == 'b')
        {
            while (stillRunning())
            {
                turnCounter++;
                GameMap playersMap = actorLocations.locationOf(player).map();
                playersMap.draw(display);
                if (turnCounter % DIO_RATE == 0 && enemies.stream().noneMatch(x -> x instanceof DIO)) //spawns an enemy DIO after specified number of turns, so long as there isn't already one on the map.
                {
                    DIO dio = new DIO("DIO", player);
                    playersMap.at(10, 10).addActor(dio);
                    enemies.add(dio);
                }

                // Process all the actors.
                for (Actor actor : actorLocations)
                {
                    if (stillRunning())
                        processActorTurn(actor);
                }

                // Tick over all the maps. For the map stuff.
                for (GameMap gameMap : gameMaps)
                {
                    gameMap.tick();
                }
            }
            display.println(endGameMessage());
        } else if (display.readChar() == 'q')
        {
            display.println(endGameMessage());
        }

    }

    void connectMaps(GameMap map1, GameMap map2) //attaches 2 maps together, map2 goes on top of map1 in this way.
    { //TODO: Make a method that creates the exits to avoid duplication
        for (Integer i : map1.getXRange())
        {

            if (map2.getXRange().contains(i))
            {
                map1.at(i, map1.getYRange().min()).addExit(new Exit("North", map2.at(i, map2.getYRange().max()), "8"));
                map2.at(i, map2.getYRange().max()).addExit(new Exit("South", map1.at(i, map1.getYRange().min()), "2"));
                if (i == map2.getXRange().min()) //Westmost section of the map - unable to move further west so only add east exits
                {
                    map1.at(i, map1.getYRange().min()).addExit(new Exit("North-East", map2.at(i + 1, map2.getYRange().max()), "9"));
                    map2.at(i, map2.getYRange().max()).addExit(new Exit("South-East", map1.at(i + 1, map1.getYRange().min()), "3"));
                } else if (i == map2.getXRange().max()) //Eastmost section of the map - Unable to move any further East so only add west exits
                {
                    map1.at(i, map1.getYRange().min()).addExit(new Exit("North-West", map2.at(i - 1, map2.getYRange().max()), "7"));
                    map2.at(i, map2.getYRange().max()).addExit(new Exit("South-West", map1.at(i - 1, map1.getYRange().min()), "1"));
                } else
                {
                    map1.at(i, map1.getYRange().min()).addExit(new Exit("North-East", map2.at(i + 1, map2.getYRange().max()), "9"));
                    map2.at(i, map2.getYRange().max()).addExit(new Exit("South-East", map1.at(i + 1, map1.getYRange().min()), "3"));
                    map1.at(i, map1.getYRange().min()).addExit(new Exit("North-West", map2.at(i - 1, map2.getYRange().max()), "7"));
                    map2.at(i, map2.getYRange().max()).addExit(new Exit("South-West", map1.at(i - 1, map1.getYRange().min()), "1"));
                }
            }
        }
    }
}
