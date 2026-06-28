import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;

public class WatcherBoss extends BossEnemy
{
    public WatcherBoss(ArrayList<Point> path, int wave)
    {
        super(path, wave);
        health    = 800 + wave * 80;
        maxHealth = health;
        speed     = 0.6;
        baseSpeed = 0.6;
        reward    = 100 + wave * 10;
    }

    public Color getColor() { return new Color(60, 60, 60); }

    public void draw(Graphics g)
    {
        super.draw(g);
        g.setColor(new Color(220, 30, 30));
        g.fillOval((int)x - 5, (int)y - 4, 4, 4);
        g.fillOval((int)x + 1, (int)y - 4, 4, 4);
    }
}