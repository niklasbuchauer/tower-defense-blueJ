import java.awt.Point;
import java.util.ArrayList;

public class TankEnemy extends Enemy
{
    public TankEnemy(ArrayList<Point> path)
    {
        super(path, 300, 1.2, 40);
    }
}