import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

public class IceSplinterEffect
{
    private double x;
    private double y;
    private int radius;
    private int damage;
    private int timer;
    private ArrayList<Enemy> enemies;

    public IceSplinterEffect(double x, double y, int radius, int damage, ArrayList<Enemy> enemies)
    {
        this.x       = x;
        this.y       = y;
        this.radius  = radius;
        this.damage  = damage;
        this.timer   = 20; // kurze Animation
        this.enemies = enemies;

        // Sofortiger Splitter-Schaden
        for (Enemy e : enemies)
        {
            if (e.isDead()) continue;
            double dx = e.getX() - x;
            double dy = e.getY() - y;
            if (Math.sqrt(dx * dx + dy * dy) <= radius)
                e.takeDamage(damage);
        }
    }

    public void update() { timer--; }
    public boolean isFinished() { return timer <= 0; }

    public void draw(Graphics g)
    {
        int alpha = (int)(200.0 * timer / 20);
        g.setColor(new Color(180, 230, 255, alpha));
        g.drawOval((int)x - radius, (int)y - radius, radius * 2, radius * 2);
        g.setColor(new Color(150, 210, 255, alpha / 2));
        g.fillOval((int)x - radius, (int)y - radius, radius * 2, radius * 2);
    }
}