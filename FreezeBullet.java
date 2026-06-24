import java.awt.Color;
import java.awt.Graphics;

public class FreezeBullet extends Bullet
{
    private int freezeDuration;

    public FreezeBullet(double x, double y, Enemy target, int damage, int freezeDuration)
    {
        super(x, y, target, damage);
        this.freezeDuration = freezeDuration;
    }

    protected void onHit()
    {
        target.takeDamage(damage);
        target.applyFreeze(freezeDuration);
    }

    public void draw(Graphics g)
    {
        g.setColor(new Color(100, 200, 255));
        g.fillOval((int)x - 4, (int)y - 4, 8, 8);
    }
}
