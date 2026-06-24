import java.util.ArrayList;

public class MapSelector
{
    private int selectedMap;

    public MapSelector()
    {
        selectedMap = 1;
    }

    public void nextMap()
    {
        selectedMap++;
        if (selectedMap > 2)
            selectedMap = 1;
    }

    public GameMap getMap()
    {
        if (selectedMap == 1)
            return new GameMap(MapType.EASY);

        return new GameMapAlt();
    
    }
}