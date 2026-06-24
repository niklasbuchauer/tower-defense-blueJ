import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

public class FreezeTower extends Tower
{
    public FreezeTower(int x, int y)
    {
        super(x, y);

        range    = 130;
        damage   = 3;
        fireRate = 40;
    }

    public void update(ArrayList<Enemy> enemies, ArrayList<Bullet> bullets)
    {
        if (cooldown > 0)
        {
            cooldown--;
            return;
        }

        Enemy target = findClosestEnemy(enemies);

        if (target != null)
        {
            // Freeze-Dauer: 120 Frames (~2 Sekunden), pro Level +30 Frames
            int freezeDuration = 120 + (level - 1) * 30;
            bullets.add(new FreezeBullet(x, y, target, damage, freezeDuration));
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

            if (Math.sqrt(dx * dx + dy * dy) <= range)
                return e;
        }
        return null;
    }

    public void draw(Graphics g)
    {
        g.setColor(new Color(100, 200, 255));
        g.fillRect(x - 15, y - 15, 30, 30);
        g.setColor(Color.BLACK);
        g.drawRect(x - 15, y - 15, 30, 30);
        g.drawString("F" + level, x - 6, y + 5);
    }
}
