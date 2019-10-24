package game;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * Simple way of adding variety to in-game text.
 */
public class TextMap
{

    private Map<String, List<String>> textMap = new HashMap<>();
    private Random random = new Random();

    public void addEntries(String string, List<String> flavour)
    {
        textMap.put(string, flavour);
    }

    public String randomText(String string)
    {
        List<String> options = textMap.get(string);
        return options.get(random.nextInt(options.size()));
    }
}
