import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;

public class GameMap
{
    public static final int TILE_SIZE = 40;

    private Tile[][] tiles;
    private ArrayList<Point> path;

    private MapType type;

    public GameMap(MapType type)
    {
        this.type = type;

        tiles = new Tile[15][20];
        path = new ArrayList<>();

        createMap();
    }

    private void createMap()
    {
        for (int r = 0; r < 15; r++)
        {
            for (int c = 0; c < 20; c++)
            {
                tiles[r][c] = new Tile(r, c, false, true);
            }
        }

        path.clear();

        if (type == MapType.EASY)
        {
            createEasyMap();
        }
        else if (type == MapType.CURVED)
        {
            createCurvedMap();
        }
    }

    private void createEasyMap()
    {
        for (int c = 0; c < 10; c++)
        {
            tiles[7][c] = new Tile(7, c, true, false);
            path.add(new Point(c * TILE_SIZE + 20, 7 * TILE_SIZE + 20));
        }

        for (int r = 7; r < 12; r++)
        {
            tiles[r][9] = new Tile(r, 9, true, false);
            path.add(new Point(9 * TILE_SIZE + 20, r * TILE_SIZE + 20));
        }

        for (int c = 9; c < 20; c++)
        {
            tiles[11][c] = new Tile(11, c, true, false);
            path.add(new Point(c * TILE_SIZE + 20, 11 * TILE_SIZE + 20));
        }
    }

    private void createCurvedMap()
    {
        for (int c = 0; c < 20; c++)
        {
            int r = (int)(7 + Math.sin(c * 0.5) * 3);

            tiles[r][c] = new Tile(r, c, true, false);
            path.add(new Point(c * TILE_SIZE + 20, r * TILE_SIZE + 20));
        }
    }

    public ArrayList<Point> getPath()
    {
        return path;
    }

    public boolean canBuild(int x, int y)
    {
        int col = x / TILE_SIZE;
        int row = y / TILE_SIZE;

        if (row < 0 || row >= 15 || col < 0 || col >= 20)
            return false;

        return tiles[row][col].isBuildable();
    }

    public boolean isPath(int x, int y)
    {
        int col = x / TILE_SIZE;
        int row = y / TILE_SIZE;

        if (row < 0 || row >= 15 || col < 0 || col >= 20)
            return true;

        return tiles[row][col].isPath();
    }

    public void draw(Graphics g)
    {
        for (int r = 0; r < 15; r++)
        {
            for (int c = 0; c < 20; c++)
            {
                Tile tile = tiles[r][c];

                if (tile.isPath())
                    g.setColor(Color.ORANGE);
                else
                    g.setColor(Color.GREEN);

                g.fillRect(c * TILE_SIZE, r * TILE_SIZE, TILE_SIZE, TILE_SIZE);

                g.setColor(Color.BLACK);
                g.drawRect(c * TILE_SIZE, r * TILE_SIZE, TILE_SIZE, TILE_SIZE);
            }
        }
    }
}