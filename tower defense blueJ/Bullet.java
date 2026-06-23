import java.awt.Color;
import java.awt.Graphics;

public class Bullet
{
    private double x;
    private double y;

    private Enemy target;

    private double speed;
    private int damage;

    private boolean active;

    public Bullet(double x, double y, Enemy target, int damage)
    {
        this.x = x;
        this.y = y;
        this.target = target;

        speed = 8;
        this.damage = damage;
        active = true;
    }

    public void update()
    {
        if (target == null || target.isDead())
        {
            active = false;
            return;
        }

        double dx = target.getX() - x;
        double dy = target.getY() - y;

        double dist = Math.sqrt(dx * dx + dy * dy);

        if (dist < speed)
        {
            target.takeDamage(damage);
            active = false;
        }
        else
        {
            x += speed * dx / dist;
            y += speed * dy / dist;
        }
    }

    public boolean isActive()
    {
        return active;
    }

    public void draw(Graphics g)
    {
        g.setColor(Color.YELLOW);
        g.fillOval((int)x - 3, (int)y - 3, 6, 6);
    }
}
