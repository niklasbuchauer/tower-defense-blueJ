import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

public abstract class Tower
{
    public int getX() { return x; }
    public int getY() { return y; }
    protected int x;
    protected int y;

    protected int range;
    protected int damage;
    protected int fireRate;
    protected int cooldown;

    protected int level;

    public Tower(int x, int y)
    {
        this.x = x;
        this.y = y;
        level = 1;
        cooldown = 0;
    }

    public void update(ArrayList<Enemy> enemies, ArrayList<Bullet> bullets)
    {
        if (cooldown > 0)
        {
            cooldown--;
            return;
        }

        Enemy target = findTarget(enemies);

        if (target != null)
        {
            bullets.add(new Bullet(x, y, target, damage));
            cooldown = fireRate;
        }
    }

    private Enemy findTarget(ArrayList<Enemy> enemies)
    {
        Enemy best = null;

        for (Enemy e : enemies)
        {
            if (e.isDead())
            {
                continue;
            }

            double dx = e.getX() - x;
            double dy = e.getY() - y;

            double distance = Math.sqrt(dx * dx + dy * dy);

            if (distance <= range)
            {
                best = e;
                break;
            }
        }

        return best;
    }

    public void upgrade()
    {
        level++;
        damage += 5;
        range += 5;

        if (fireRate > 8)
        {
            fireRate -= 2;
        }
    }

    public int getUpgradeCost()
    {
        return level * 50;
    }

    public void draw(Graphics g)
    {
        g.setColor(Color.BLUE);
        g.fillRect(x - 15, y - 15, 30, 30);

        g.setColor(Color.BLACK);
        g.drawString("" + level, x - 3, y + 5);
    }
}