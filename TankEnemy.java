import java.awt.Color;
import java.awt.Point;
import java.util.ArrayList;

public class TankEnemy extends Enemy
{
    public TankEnemy(ArrayList<Point> path, int wave)
    {
        super(path, 130 + wave * 18, 0.55, 30 + wave * 4);
    }

    public Color getColor() { return new Color(80, 80, 200); }
}
