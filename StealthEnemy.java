import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Composite;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.util.ArrayList;

public class StealthEnemy extends Enemy
{
    public StealthEnemy(ArrayList<Point> path, int wave)
    {
        super(path, 60 + wave * 10, 1.2 + wave * 0.04, 15 + wave * 2);
        this.stealth = true;
    }

    public Color getColor() { return new Color(80, 80, 80); }

    public void draw(Graphics g)
    {
        Graphics2D g2 = (Graphics2D) g;
        Composite old = g2.getComposite();
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f));
        super.draw(g);
        // Flimmernder Rand
        g2.setColor(new Color(150, 150, 150));
        g2.drawOval((int)x - 13, (int)y - 13, 26, 26);
        g2.setComposite(old);
    }
}