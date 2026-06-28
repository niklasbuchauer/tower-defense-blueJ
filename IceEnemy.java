import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;

public class IceEnemy extends Enemy
{
    private int slowTimer = 0;

    public IceEnemy(ArrayList<Point> path, int wave)
    {
        super(path, 75 + wave * 11, 0.85 + wave * 0.03, 16 + wave * 2);
    }

    // Tower-Verlangsamung wird in Game.java abgefragt
    public boolean isSlowingTowers() { return !isDead(); }

    public Color getColor() { return new Color(150, 220, 255); }

    public void draw(Graphics g)
    {
        super.draw(g);
        // Schneeflocken-Andeutung
        g.setColor(Color.WHITE);
        g.drawLine((int)x - 6, (int)y, (int)x + 6, (int)y);
        g.drawLine((int)x, (int)y - 6, (int)x, (int)y + 6);
        g.drawLine((int)x - 4, (int)y - 4, (int)x + 4, (int)y + 4);
        g.drawLine((int)x + 4, (int)y - 4, (int)x - 4, (int)y + 4);
    }
}