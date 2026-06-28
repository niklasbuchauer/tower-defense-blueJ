import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

public class SniperTower extends Tower
{
    private boolean detectStealth = false;
    private ArrayList<Enemy> enemyRef;

    public SniperTower(int x, int y)
    {
        super(x, y);
        range    = 220;
        damage   = 50;
        fireRate = 70;
    }

    public void update(ArrayList<Enemy> enemies, ArrayList<Bullet> bullets)
    {
        this.enemyRef = enemies;
        if (cooldown > 0) { cooldown--; return; }

        Enemy target = findTarget(enemies);
        if (target != null)
        {
            shoot(target, bullets, enemies);
            totalDamageDealt += damage;
            cooldown = fireRate;
        }
    }

    private Enemy findTarget(ArrayList<Enemy> enemies)
    {
        for (Enemy e : enemies)
        {
            if (e.isDead()) continue;
            if (e.isStealth() && !detectStealth) continue;
            double dx = e.getX() - x;
            double dy = e.getY() - y;
            if (Math.sqrt(dx * dx + dy * dy) <= range) return e;
        }
        return null;
    }

    private void shoot(Enemy target, ArrayList<Bullet> bullets, ArrayList<Enemy> enemies)
    {
        if (level >= 5)
        {
            // Railgun: trifft ALLE Gegner auf der Linie
            bullets.add(new RailgunBullet(x, y, target, damage * 3, enemies));
        }
        else if (level >= 4)
        {
            // Kopfschuss: 30% Instant-Kill-Chance
            bullets.add(new SniperBullet(x, y, target, damage, true));
        }
        else
        {
            bullets.add(new Bullet(x, y, target, damage));
        }
    }

    public void upgrade()
    {
        if (level >= 5) return;
        level++;
        switch (level)
        {
            case 2: range = 340; break;
            case 3: detectStealth = true; break;
            case 4: damage += 30; break;
            case 5: damage += 80; break;
        }
    }

    public int getUpgradeCost()
    {
        switch (level)
        {
            case 1: return 100;   // jeweilige Preise
            case 2: return 250;
            case 3: return 600;
            case 4: return 1500;
            default: return Integer.MAX_VALUE; // Lvl 5 = kein Upgrade mehr
        }
    }

    public String getUpgradeDescription()
    {
        switch (level)
        {
            case 1: return "Maximale Reichweite";
            case 2: return "Erkennt getarnte Gegner";
            case 3: return "Kopfschuss (30% Instant-Kill)";
            case 4: return "Railgun (durchdringt alle)";
        }
        return "Max Level";
    }

    public void draw(Graphics g)
    {
        Color c = level >= 5 ? new Color(255, 0, 100)
                : level >= 3 ? new Color(180, 0, 180)
                : Color.MAGENTA;

        g.setColor(c);
        g.fillRect(x - 15, y - 15, 30, 30);
        g.setColor(Color.WHITE);
        g.drawString("" + level, x - 3, y + 5);
        if (detectStealth)
        {
            g.setColor(Color.YELLOW);
            g.drawString("👁", x - 5, y - 17);
        }
    }
}