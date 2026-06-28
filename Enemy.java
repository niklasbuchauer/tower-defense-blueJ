import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;

public class Enemy
{
    protected double x;
    protected double y;

    protected double speed;
    protected double baseSpeed;
    protected int health;
    protected int maxHealth;
    protected int reward;

    protected int currentPoint;

    private int freezeTimer;
    private PoisonEffect poison = null;
    
    protected boolean stealth = false;
    
    public boolean isStealth() { return stealth; }

    public Enemy(ArrayList<Point> path, int health, double speed, int reward)
    {
        this.health    = health;
        this.maxHealth = health;
        this.speed     = speed;
        this.baseSpeed = speed;
        this.reward    = reward;
        this.freezeTimer = 0;

        x = path.get(0).x;
        y = path.get(0).y;

        currentPoint = 1;
    }

    public void applyPoison(int damage, int ticks, int tickInterval)
    {
        poison = new PoisonEffect(damage, ticks, tickInterval);
    }

    public boolean isPoisoned() { return poison != null && !poison.isFinished(); }
    
    public void update(ArrayList<Point> path)
    {
        if (currentPoint >= path.size())
            return;

        // Giftschaden
        if (poison != null)
        {
            int dmg = poison.update();
            if (dmg > 0) takeDamage(dmg);
            if (poison.isFinished()) poison = null;
        }
            
        // Freeze-Effekt: Speed reduzieren
        if (freezeTimer > 0)
        {
            freezeTimer--;
            speed = baseSpeed * 0.35;
        }
        else
        {
            speed = baseSpeed;
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

    public void applyFreeze(int durationFrames)
    {
        freezeTimer = durationFrames;
    }

    public boolean isFrozen()
    {
        return freezeTimer > 0;
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

    public int getReward()  { return reward; }
    public double getX()    { return x; }
    public double getY()    { return y; }

    public void setReward(int r)
    {
        reward = r;
    }

    public Color getColor() { return Color.RED; }

    public void draw(Graphics g)
    {
    // Gegner-Körper – blau wenn eingefroren - grün wenn poisend
    if (isFrozen())
        g.setColor(new Color(100, 180, 255));
    else if (isPoisoned())
        g.setColor(new Color(80, 200, 80));
    else
        g.setColor(getColor());

        g.fillOval((int)x - 12, (int)y - 12, 24, 24);

        // Lebensbalken
        g.setColor(Color.BLACK);
        g.drawRect((int)x - 15, (int)y - 22, 30, 5);
        g.setColor(Color.GREEN);
        int width = (int)(30.0 * health / maxHealth);
        g.fillRect((int)x - 15, (int)y - 22, width, 5);
    }
}
