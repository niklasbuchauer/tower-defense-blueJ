import java.awt.Color;
import java.awt.Graphics;

public class FreezeTower extends Tower
{
    public FreezeTower(int x, int y)
    {
        super(x, y);

        range = 120;
        damage = 5;
        fireRate = 12;
    }

    public void draw(Graphics g)
    {
        g.setColor(Color.WHITE);
        g.fillRect(x - 15, y - 15, 30, 30);

        g.setColor(Color.BLACK);
        g.drawRect(x - 15, y - 15, 30, 30);

        g.drawString("" + level, x - 3, y + 5);
    }
}