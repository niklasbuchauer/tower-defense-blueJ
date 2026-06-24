public class Tile
{
    private int row;
    private int col;
    private boolean path;
    private boolean buildable;

    public Tile(int row, int col, boolean path, boolean buildable)
    {
        this.row = row;
        this.col = col;
        this.path = path;
        this.buildable = buildable;
    }

    public int getRow()
    {
        return row;
    }

    public int getCol()
    {
        return col;
    }

    public boolean isPath()
    {
        return path;
    }

    public boolean isBuildable()
    {
        return buildable;
    }
}