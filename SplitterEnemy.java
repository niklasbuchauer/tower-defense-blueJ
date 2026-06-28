import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;

public class SplitterEnemy extends Enemy
{
    private boolean isShard; // true = bereits ein Fragment
    
    private boolean shardsSpawned = false;

    public boolean hasSpawnedShards() { return shardsSpawned; }
    public void markShardsSpawned()   { shardsSpawned = true; }

    public SplitterEnemy(ArrayList<Point> path, int wave, boolean isShard)
    {
        super(path,
            isShard ? 20 + wave * 3 : 80 + wave * 12,
            isShard ? 1.8 + wave * 0.05 : 1.0 + wave * 0.04,
            isShard ? 5 + wave : 15 + wave * 2);
        this.isShard = isShard;
    }

    public boolean isShard() { return isShard; }

    public Color getColor() { return new Color(140, 60, 200); }

    public void draw(Graphics g)
    {
        super.draw(g);
        // Risse zeichnen
        g.setColor(new Color(200, 150, 255));
        int cx = (int)x, cy = (int)y;
        g.drawLine(cx - 8, cy - 4, cx, cy + 4);
        g.drawLine(cx + 6, cy - 6, cx - 2, cy + 2);
    }
}