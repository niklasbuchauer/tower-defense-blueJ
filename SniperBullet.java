import java.awt.Color;
import java.awt.Graphics;

public class SniperBullet extends Bullet
{
    private boolean canHeadshot;

    public SniperBullet(double x, double y, Enemy target, int damage, boolean canHeadshot)
    {
        super(x, y, target, damage);
        this.canHeadshot = canHeadshot;
        this.speed = 14;
    }

    protected void onHit()
    {
        if (canHeadshot && Math.random() < 0.30)
            target.takeDamage(99999); // Instant-Kill
        else
            target.takeDamage(damage);
    }

    public void draw(Graphics g)
    {
        g.setColor(new Color(255, 50, 200));
        g.fillOval((int)x - 3, (int)y - 3, 6, 6);
    }
}