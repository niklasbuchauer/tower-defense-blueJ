import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;

public class RegenEnemy extends Enemy
{
    private int regenTimer = 0;
    private int regenAmount;

    public RegenEnemy(ArrayList<Point> path, int wave)
    {
        super(path, 70 + wave * 12, 1.0 + wave * 0.04, 14 + wave * 2);
        this.regenAmount = 3 + wave / 5;
    }

    public void update(ArrayList<Point> path)
    {
        super.update(path);
        regenTimer++;
        if (regenTimer >= 60)
        {
            regenTimer = 0;
            health = Math.min(health + regenAmount, maxHealth);
        }
    }

    public Color getColor() { return new Color(200, 50, 50); }

    public void draw(Graphics g)
    {
        super.draw(g);
        // Grünes Plus
        g.setColor(new Color(50, 220, 50));
        g.fillRect((int)x - 1, (int)y - 6, 3, 12);
        g.fillRect((int)x - 6, (int)y - 1, 12, 3);
    }
}