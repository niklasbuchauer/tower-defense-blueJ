import java.awt.Color;
import java.awt.Graphics;

public class RapidTower extends Tower
{
    public RapidTower(int x, int y)
    {
        super(x, y);

        range = 90;
        damage = 8;
        fireRate = 8;
    }

    public void draw(Graphics g)
    {
        g.setColor(Color.CYAN);
        g.fillRect(x - 15, y - 15, 30, 30);

        g.setColor(Color.BLACK);
        g.drawString("" + level, x - 3, y + 5);
    }
}