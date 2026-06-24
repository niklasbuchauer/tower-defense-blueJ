import java.awt.Color;
import java.awt.Graphics;

public class SniperTower extends Tower
{
    public SniperTower(int x, int y)
    {
        super(x, y);

        range = 220;
        damage = 50;
        fireRate = 70;
    }

    public void draw(Graphics g)
    {
        g.setColor(Color.MAGENTA);
        g.fillRect(x - 15, y - 15, 30, 30);

        g.setColor(Color.BLACK);
        g.drawString("" + level, x - 3, y + 5);
    }
}