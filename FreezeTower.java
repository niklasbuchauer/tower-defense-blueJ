import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

public class FreezeTower extends Tower
{
    private ArrayList<IceSplinterEffect> splinters = new ArrayList<>();
    private int blizzardTimer = 0;

    public FreezeTower(int x, int y)
    {
        super(x, y);
        range    = 130;
        damage   = 3;
        fireRate = 40;
    }

    public void update(ArrayList<Enemy> enemies, ArrayList<Bullet> bullets)
    {
        // Blizzard (Lvl 5): permanentes Slow-Feld
        if (level >= 5)
        {
            blizzardTimer++;
            for (Enemy e : enemies)
            {
                if (e.isDead()) continue;
                double dx = e.getX() - x;
                double dy = e.getY() - y;
                if (Math.sqrt(dx * dx + dy * dy) <= range)
                    e.applyFreeze(10); // konstant eingefroren halten
            }
            // Alle 180 Frames: kurzes Einfrieren
            if (blizzardTimer >= 180)
            {
                blizzardTimer = 0;
                for (Enemy e : enemies)
                {
                    if (e.isDead()) continue;
                    double dx = e.getX() - x;
                    double dy = e.getY() - y;
                    if (Math.sqrt(dx * dx + dy * dy) <= range)
                        e.applyFreeze(90);
                }
            }
        }

        // Eissplitter (Lvl 4): auftauende Gegner beschädigen
        if (level >= 4)
        {
            for (Enemy e : new ArrayList<>(enemies))
            {
                if (e.isDead()) continue;
                double dx = e.getX() - x;
                double dy = e.getY() - y;
                if (Math.sqrt(dx * dx + dy * dy) <= range + 40)
                {
                    // NEU: nur spawnen wenn Gegner GERADE aufgetaut ist
                    if (!e.isFrozen() && e.wasJustUnfrozen())
                    {
                        splinters.add(new IceSplinterEffect(e.getX(), e.getY(), 50, 15, enemies));
                        e.resetUnfrozenFlag(); // NEU
                    }
                }
            }
            splinters.removeIf(s -> s.isFinished());
        }

        if (cooldown > 0) { cooldown--; return; }

        Enemy target = findClosestEnemy(enemies);
        if (target != null)
        {
            int freezeDuration = 120 + (level - 1) * 30;
            boolean aoe = level >= 3;

            if (aoe)
            {
                // Flächen-Freeze
                for (Enemy e : enemies)
                {
                    if (e.isDead()) continue;
                    double dx = e.getX() - target.getX();
                    double dy = e.getY() - target.getY();
                    if (Math.sqrt(dx * dx + dy * dy) <= 70)
                    {
                        bullets.add(new FreezeBullet(x, y, e, damage, freezeDuration));
                    }
                }
            }
            else
            {
                bullets.add(new FreezeBullet(x, y, target, damage, freezeDuration));
            }

            totalDamageDealt += damage;
            cooldown = fireRate;
        }
    }

    private Enemy findClosestEnemy(ArrayList<Enemy> enemies)
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

    public void upgrade()
    {
        if (level >= 5) return;
        level++;
        switch (level)
        {
            case 2: fireRate = 30; break;
            case 3: damage += 2; break;
            case 4: damage += 5; break;
            case 5: range += 30; break;
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
            case 1: return "Längere Freeze-Dauer"; 
            case 2: return "Flächen-Freeze (AoE)";
            case 3: return "Eissplitter beim Auftauen";
            case 4: return "Blizzard-Modus";
        }
        return "Max Level";
    }

    public void draw(Graphics g)
    {
        // Splinter-Effekte zeichnen
        for (IceSplinterEffect s : splinters) s.draw(g);

        // Blizzard-Aura
        if (level >= 5)
        {
            g.setColor(new Color(150, 220, 255, 40));
            g.fillOval(x - range, y - range, range * 2, range * 2);
        }

        Color c = level >= 5 ? new Color(0, 100, 200)
                : level >= 3 ? new Color(50, 150, 220)
                : new Color(100, 200, 255);

        g.setColor(c);
        g.fillRect(x - 15, y - 15, 30, 30);
        g.setColor(Color.BLACK);
        g.drawRect(x - 15, y - 15, 30, 30);
        g.drawString("F" + level, x - 6, y + 5);
    }
}