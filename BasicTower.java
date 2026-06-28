import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

public class BasicTower extends Tower
{
    private ArrayList<Enemy> enemyRef;

    public BasicTower(int x, int y)
    {
        super(x, y);
        range    = 120;
        damage   = 18;
        fireRate = 30;
    }

    public void setEnemyRef(ArrayList<Enemy> enemies) { this.enemyRef = enemies; }

    public void update(ArrayList<Enemy> enemies, ArrayList<Bullet> bullets)
    {
        this.enemyRef = enemies;
        super.update(enemies, bullets);
    }

    protected void shoot(Enemy target, ArrayList<Bullet> bullets)
    {
        boolean elite = level >= 5;

        if (level >= 4)
        {
            // Explosiv + Durchschuss
            int splash = elite ? 80 : 50;
            bullets.add(new ExplosionBullet(x, y, target, damage, enemyRef, splash, elite));
        }
        else if (level >= 3)
        {
            // Durchschuss: trifft 2-3 Gegner
            int pierce = elite ? 3 : 2;
            bullets.add(new PierceBullet(x, y, target, damage, enemyRef, pierce, false));
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
            case 2: fireRate = (int)(fireRate * 0.75); break;
            case 3: damage += 5; break;
            case 4: damage += 10; break;
            case 5: damage += 20; break;
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
            case 1: return "Schnellere Schüsse";
            case 2: return "Durchschuss (2-3 Gegner)";
            case 3: return "Explosive Schüsse (AoE)";
            case 4: return "Elite-Munition (Mega AoE)";
        }
        return "Max Level";
    }

    public void draw(Graphics g)
    {
        Color c = level >= 5 ? new Color(255, 80, 0)
                : level >= 4 ? new Color(220, 50, 50)
                : level >= 3 ? new Color(180, 60, 60)
                : level >= 2 ? new Color(100, 100, 200)
                : Color.BLUE;

        g.setColor(c);
        g.fillRect(x - 15, y - 15, 30, 30);
        g.setColor(Color.WHITE);
        g.drawString("" + level, x - 3, y + 5);
    }
}