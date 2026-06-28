import java.awt.Color;
import java.awt.Graphics;

public class PoisonBullet extends Bullet
{
    private boolean poisonEnabled;

    public PoisonBullet(double x, double y, Enemy target, int damage, boolean poisonEnabled)
    {
        super(x, y, target, damage);
        this.poisonEnabled = poisonEnabled;
    }

    protected void onHit()
    {
        target.takeDamage(damage);
        if (poisonEnabled)
            target.applyPoison(3, 8, 20); // 3 DMG, 8 Ticks, alle 20 Frames
    }

    public void draw(Graphics g)
    {
        g.setColor(poisonEnabled ? new Color(100, 255, 100) : Color.CYAN);
        g.fillOval((int)x - 3, (int)y - 3, 6, 6);
    }
}