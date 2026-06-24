import java.awt.Color;
import java.awt.Point;
import java.util.ArrayList;

public class BossEnemy extends Enemy
{
    public BossEnemy(ArrayList<Point> path, int wave)
    {
        super(path, 600 + wave * 70, 0.45, 180 + wave * 15);
    }

    public Color getColor() { return new Color(160, 0, 0); }
}
