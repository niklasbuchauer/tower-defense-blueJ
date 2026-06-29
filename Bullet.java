import java.awt.Color;
import java.awt.Graphics;

public class Bullet
{
    protected double x;
    protected double y;

    protected Enemy target;
    protected double speed;
    protected int damage;
    protected boolean active;

    public Bullet(double x, double y, Enemy target, int damage)
    {
        this.x      = x;
        this.y      = y;
        this.target = target;
        this.speed  = 8;
        this.damage = damage;
        this.active = true;
    }

    public void update(Game game) // <-- Parameter 'Game game' hinzugefügt
    {
        if (target == null || target.isDead())
        {
            active = false;
            return;
        }

        double dx = target.getX() - x;
        double dy = target.getY() - y;
        double dist = Math.sqrt(dx * dx + dy * dy);

        if (dist < speed)
        {
            onHit(game); // <-- game weitergeben
            active = false;
        }
        else
        {
            x += speed * dx / dist;
            y += speed * dy / dist;
        }
    }

    // Kann von Unterklassen überschrieben werden (z.B. FreezeBullet)
    protected void onHit(Game game) // <-- Parameter 'Game game' hinzugefügt
    {
        target.takeDamage(damage);
        // Spawnt eine standardmäßige rote Schadenszahl
        game.addFloatingText(target.getX(), target.getY(), "-" + damage, Color.RED);
    }

    public boolean isActive()   { return active; }
    public Enemy  getTarget()   { return target; }
    public double getTargetX()  { return x; }
    public double getTargetY()  { return y; }

    public void draw(Graphics g)
    {
        g.setColor(Color.YELLOW);
        g.fillOval((int)x - 3, (int)y - 3, 6, 6);
    }
}
