import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

public class ExplosionBullet extends Bullet
{
    private ArrayList<Enemy> allEnemies;
    private int splashRadius;
    private boolean elite; // Lvl 5 BasicTower

    public ExplosionBullet(double x, double y, Enemy target, int damage,
                           ArrayList<Enemy> allEnemies, int splashRadius, boolean elite)
    {
        super(x, y, target, damage);
        this.allEnemies   = allEnemies;
        this.splashRadius = splashRadius;
        this.elite        = elite;
    }

    protected void onHit()
    {
        // AoE-Schaden
        for (Enemy e : allEnemies)
        {
            if (e.isDead()) continue;
            double dx = e.getX() - target.getX();
            double dy = e.getY() - target.getY();
            if (Math.sqrt(dx * dx + dy * dy) <= splashRadius)
            {
                int dealt = elite ? damage * 2 : damage;
                e.takeDamage(dealt);
            }
        }
    }

    public void draw(Graphics g)
    {
        g.setColor(elite ? new Color(255, 120, 0) : Color.ORANGE);
        g.fillOval((int)x - 5, (int)y - 5, 10, 10);
    }
}