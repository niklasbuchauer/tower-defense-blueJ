import java.awt.Color;
import java.awt.Point;
import java.util.ArrayList;

public class FastEnemy extends Enemy
{
    public FastEnemy(ArrayList<Point> path, int wave)
    {
        super(path, 30 + wave * 5, 1.9 + wave * 0.06, 8 + wave);
    }

    public Color getColor() { return new Color(255, 200, 0); }
}
