import java.awt.Point;
import java.util.ArrayList;

public class FastEnemy extends Enemy
{
    public FastEnemy(ArrayList<Point> path)
    {
        super(path, 60, 3.5, 15);
    }
}