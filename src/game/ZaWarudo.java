package game;

import edu.monash.fit2099.engine.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static java.util.Map.entry;

/**
 * Modified version of World. The name is just a reference to the anime JoJos Bizarre Adventure.
 */
public class ZaWarudo extends World
{
    private static final int DIO_RATE = 40;
    private int turnCounter = 0;
    private List<Enemy> enemies = new ArrayList<>();
    private Actor timeStopActor; //Just something for fun - a way to have DIOs signature time-stop ability in the game.
    private int timeStopTurns;
    private Map<String, String> noNumpadMap; //for people who don't have numpads (tkl gang rise up) - now the moveActions are set to qweadzxc

    /**
     * Constructor.
     *
     * @param display the Display that will display this World.
     */
    public ZaWarudo(Display display)
    {
        super(display);
        noNumpadMap = Map.ofEntries(
                entry("1", "z"),
                entry("2", "x"),
                entry("3", "c"),
                entry("4", "a"),
                entry("6", "d"),
                entry("7", "q"),
                entry("8", "w"),
                entry("9", "e")
        );
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
                    DIO dio = new DIO("DIO", player, this);
                    playersMap.at(10, 10).addActor(dio);
                    enemies.add(dio);
                }

                // Process all the actors.
                for (Actor actor : actorLocations)
                {
                    if (stillRunning() && (timeStopTurns == 0 || timeStopActor.equals(actor))) //only processes turns when timestop is inactive, or this actor is controlling time-stop
                        processActorTurn(actor);
                }
                if (timeStopTurns > 0 && --timeStopTurns == 0)
                {
                    timeStopActor = null; //nulls time stop actor when the number of turns is up.
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

    @Override
    protected void processActorTurn(Actor actor)
    {
        Location here = actorLocations.locationOf(actor);
        GameMap map = here.map();
        Actions actions = new Actions();

        for (Exit exit : here.getExits()) {
            Location destination = exit.getDestination();

            // Game rule. You don't get to interact with the ground if someone is standing
            // on it.
            if (actorLocations.isAnActorAt(destination)) {
                actions.add(actorLocations.getActorAt(destination).getAllowableActions(actor, exit.getName(), map));
            } else {
                actions.add(destination.getGround().allowableActions(actor, destination, exit.getName()));
            }
            actions.add(destination.getMoveAction(actor, exit.getName(), noNumpadMap.containsKey(exit.getHotKey()) ? noNumpadMap.get(exit.getHotKey()) : exit.getHotKey()));
        }

        for (Item item : actor.getInventory()) {
            actions.add(item.getAllowableActions());
            // Game rule. If you're carrying it, you can drop it.
            actions.add(item.getDropAction());
        }

        for (Item item : here.getItems()) {
            actions.add(item.getAllowableActions());
            // Game rule. If it's on the ground you can pick it up.
            actions.add(item.getPickUpAction());
        }
        actions.add(new DoNothingAction());

        Action action = actor.playTurn(actions, lastActionMap.get(actor), map, display);
        lastActionMap.put(actor, action);

        String result = action.execute(actor, map);
        if (!(actor instanceof SimpleCreature)) //only outputs the actors we care about.
        {
            display.println(result);
        }
    }

    void connectMaps(GameMap map1, GameMap map2) //attaches 2 maps together, map2 goes on top of map1 in this way.
    {
        for (Integer i : map1.getXRange())
        {
            if (map2.getXRange().contains(i))
            {   //you can always move up and down between the maps.
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
                } else //adds exits in all directions
                {
                    map1.at(i, map1.getYRange().min()).addExit(new Exit("North-East", map2.at(i + 1, map2.getYRange().max()), "9"));
                    map2.at(i, map2.getYRange().max()).addExit(new Exit("South-East", map1.at(i + 1, map1.getYRange().min()), "3"));
                    map1.at(i, map1.getYRange().min()).addExit(new Exit("North-West", map2.at(i - 1, map2.getYRange().max()), "7"));
                    map2.at(i, map2.getYRange().max()).addExit(new Exit("South-West", map1.at(i - 1, map1.getYRange().min()), "1"));
                }
            }
        }
    }

    void stopTime(Actor actor, int stopTurns)
    {
        this.timeStopActor = actor;
        this.timeStopTurns = stopTurns;
        display.println("Time has stopped! Only " + actor + " can move for " + timeStopTurns + " turns!");
    }

    public int getTimeStopTurns()
    {
        return timeStopTurns;
    }
}
