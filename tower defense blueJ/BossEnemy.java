import java.awt.Point;
import java.util.ArrayList;

public class BossEnemy extends Enemy
{
    public BossEnemy(ArrayList<Point> path)
    {
        super(path, 1200, 0.8, 200);
    }
}