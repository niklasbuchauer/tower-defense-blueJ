import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

public class RailgunBullet extends Bullet
{
    private ArrayList<Enemy> allEnemies;

    public RailgunBullet(double x, double y, Enemy target, int damage, ArrayList<Enemy> allEnemies)
    {
        super(x, y, target, damage);
        this.allEnemies = allEnemies;
        this.speed = 18;
    }

    protected void onHit(Game game)
    {
        // Trifft alle Gegner in einer Linie (40px Breite)
        for (Enemy e : allEnemies)
        {
            if (e.isDead()) continue;
            double dx = e.getX() - x;
            double dy = e.getY() - y;
            double dist = Math.abs(dx * Math.sin(0) - dy * Math.cos(0)); // vereinfacht
            if (dist <= 40) 
            {
                e.takeDamage(damage);
                // Ein futuristisches Magenta/Pink für die Railgun
                game.addFloatingText(e.getX(), e.getY(), "-" + damage, new Color(255, 0, 150));
            }
        }
    }

    public void draw(Graphics g)
    {
        g.setColor(new Color(255, 0, 150));
        g.fillOval((int)x - 5, (int)y - 5, 10, 10);
    }
}