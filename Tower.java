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
    protected int totalDamageDealt = 0;

    protected int level;
    
    public abstract String getUpgradeDescription();
    public abstract int getUpgradeCost();   
    
    public int getDamage()   { return damage; }
    public int getRange()    { return range; }
    public int getFireRate() { return fireRate; }
    public int getLevel()    { return level; }
    public int getTotalDamageDealt() { return totalDamageDealt; }
    
    private boolean iceSlowed = false;

    public void applyIceSlow()
    {
        if (!iceSlowed)
        {
            iceSlowed = true;
            fireRate = (int)(fireRate * 1.4);
        }
    }

    public void removeIceSlow()
    {
        if (iceSlowed)
        {
            iceSlowed = false;
            fireRate = (int)(fireRate / 1.4);
        }
    }

    public String getName()
    {
        if (this instanceof BasicTower)  return "Basic Tower";
        if (this instanceof SniperTower) return "Sniper Tower";
        if (this instanceof RapidTower)  return "Rapid Tower";
        if (this instanceof FreezeTower) return "Freeze Tower";
        return "Tower";
    }
    

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
            totalDamageDealt += damage;
            cooldown = fireRate;
        }
    }

    private Enemy findTarget(ArrayList<Enemy> enemies)
    {
        Enemy best = null;

        for (Enemy e : enemies)
        {
            if (e.isDead())
                continue;

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
        damage += 8;
        range  += 10;

        if (fireRate > 10)
            fireRate -= 3;
    }

    public void draw(Graphics g)
    {
        g.setColor(Color.BLUE);
        g.fillRect(x - 15, y - 15, 30, 30);
        g.setColor(Color.WHITE);
        g.drawString("" + level, x - 3, y + 5);
    }
}
