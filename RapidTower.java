import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

public class RapidTower extends Tower
{
    private int warmupTimer   = 0;
    private boolean warmedUp  = false;

    public RapidTower(int x, int y)
    {
        super(x, y);
        range    = 90;
        damage   = 8;
        fireRate = 8;
    }

    public void update(ArrayList<Enemy> enemies, ArrayList<Bullet> bullets)
    {
        if (cooldown > 0) { cooldown--; return; }

        Enemy target = findTarget(enemies);

        if (target == null)
        {
            if (level >= 5) warmedUp = false;
            return;
        }

        // Lvl 5: Aufwärmzeit
        if (level >= 5 && !warmedUp)
        {
            warmupTimer++;
            if (warmupTimer >= 60) { warmedUp = true; warmupTimer = 0; }
            return;
        }

        shoot(target, bullets);
        totalDamageDealt += damage;
        cooldown = level >= 5 ? 3 : fireRate;
    }

    private Enemy findTarget(ArrayList<Enemy> enemies)
    {
        for (Enemy e : enemies)
        {
            if (e.isDead()) continue;
            double dx = e.getX() - x;
            double dy = e.getY() - y;
            if (Math.sqrt(dx * dx + dy * dy) <= range) return e;
        }
        return null;
    }

    private void shoot(Enemy target, ArrayList<Bullet> bullets)
    {
        if (level >= 4)
        {
            // Doppelschuss
            bullets.add(new PoisonBullet(x - 3, y, target, damage, level >= 3));
            bullets.add(new PoisonBullet(x + 3, y, target, damage, level >= 3));
        }
        else
        {
            bullets.add(new PoisonBullet(x, y, target, damage, level >= 3));
        }
    }

    public void upgrade()
    {
        if (level >= 5) return;
        level++;
        switch (level)
        {
            case 2: fireRate = 5; break;
            case 3: damage += 3; break;
            case 4: damage += 5; break;
            case 5: damage += 8; break;
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
            case 1: return "Höhere Feuerrate";
            case 2: return "Giftmunition";
            case 3: return "Doppelschuss";
            case 4: return "Minigun (extrem schnell)";
        }
        return "Max Level";
    }

    public void draw(Graphics g)
    {
        Color c = level >= 5 ? new Color(0, 200, 100)
                : level >= 3 ? new Color(0, 160, 80)
                : Color.CYAN;

        g.setColor(c);
        g.fillRect(x - 15, y - 15, 30, 30);
        g.setColor(Color.BLACK);
        g.drawString("" + level, x - 3, y + 5);

        // Aufwärm-Indikator
        if (level >= 5 && !warmedUp && warmupTimer > 0)
        {
            g.setColor(Color.RED);
            g.fillRect(x - 15, y + 17, (int)(30.0 * warmupTimer / 60), 4);
        }
    }
}