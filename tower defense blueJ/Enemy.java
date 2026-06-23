import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;

public class Enemy
{
    protected double x;
    protected double y;

    protected double speed;
    protected int health;
    protected int maxHealth;
    protected int reward;

    protected int currentPoint;

    public Enemy(ArrayList<Point> path, int health, double speed, int reward)
    {
        this.health = health;
        this.maxHealth = health;
        this.speed = speed;
        this.reward = reward;

        x = path.get(0).x;
        y = path.get(0).y;

        currentPoint = 1;
    }

    public void update(ArrayList<Point> path)
    {
        if (currentPoint >= path.size())
        {
            return;
        }

        Point target = path.get(currentPoint);

        double dx = target.x - x;
        double dy = target.y - y;

        double distance = Math.sqrt(dx * dx + dy * dy);

        if (distance < speed)
        {
            x = target.x;
            y = target.y;
            currentPoint++;
        }
        else
        {
            x += speed * dx / distance;
            y += speed * dy / distance;
        }
    }

    public boolean reachedEnd(ArrayList<Point> path)
    {
        return currentPoint >= path.size();
    }

    public void takeDamage(int damage)
    {
        health -= damage;
    }

    public boolean isDead()
    {
        return health <= 0;
    }

    public int getReward()
    {
        return reward;
    }

    public double getX()
    {
        return x;
    }

    public double getY()
    {
        return y;
    }

    public void draw(Graphics g)
    {
        g.setColor(Color.RED);
        g.fillOval((int)x - 12, (int)y - 12, 24, 24);

        g.setColor(Color.BLACK);
        g.drawRect((int)x - 15, (int)y - 20, 30, 5);

        g.setColor(Color.GREEN);
        int width = (int)(30.0 * health / maxHealth);
        g.fillRect((int)x - 15, (int)y - 20, width, 5);
    }
    
    public void setReward(int r)
    {
        reward = r;
    }   
}
