import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;

public class NormalEnemy extends Enemy
{
    public NormalEnemy(ArrayList<Point> path, int wave)
    {
        super(path, 55 + wave * 10, 1.0 + wave * 0.04, 12 + wave * 2);
    }

    public Color getColor() { return new Color(60, 180, 60); }

    public void draw(Graphics g)
    {
        super.draw(g);
        // Augen
        g.setColor(Color.BLACK);
        g.fillOval((int)x - 5, (int)y - 5, 4, 4);
        g.fillOval((int)x + 1, (int)y - 5, 4, 4);
    }
}