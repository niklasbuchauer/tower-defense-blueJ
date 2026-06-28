import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;

public class HealerEnemy extends Enemy
{
    private int healTimer = 0;
    private static final int HEAL_RADIUS = 80;
    private static final int HEAL_AMOUNT = 5;

    public HealerEnemy(ArrayList<Point> path, int wave)
    {
        super(path, 65 + wave * 10, 0.9 + wave * 0.03, 18 + wave * 2);
    }

    public void updateHealer(ArrayList<Enemy> allEnemies, ArrayList<Point> path)
    {
        super.update(path);
        healTimer++;
        if (healTimer >= 90)
        {
            healTimer = 0;
            for (Enemy e : allEnemies)
            {
                if (e == this || e.isDead()) continue;
                double dx = e.getX() - x;
                double dy = e.getY() - y;
                if (Math.sqrt(dx*dx + dy*dy) <= HEAL_RADIUS)
                    e.heal(HEAL_AMOUNT);
            }
        }
    }

    public Color getColor() { return new Color(220, 180, 0); }

    public void draw(Graphics g)
    {
        super.draw(g);
        g.setColor(Color.WHITE);
        g.fillRect((int)x - 1, (int)y - 6, 3, 12);
        g.fillRect((int)x - 6, (int)y - 1, 12, 3);
    }
}