import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;

public class ArmoredEnemy extends Enemy
{
    private int armor; // Schaden wird reduziert

    public ArmoredEnemy(ArrayList<Point> path, int wave)
    {
        super(path, 100 + wave * 15, 0.9 + wave * 0.03, 20 + wave * 3);
        this.armor = 5 + wave / 4;
    }

    public void takeDamage(int damage)
    {
        int reduced = Math.max(1, damage - armor);
        super.takeDamage(reduced);
    }

    public Color getColor() { return new Color(160, 160, 160); }

    public void draw(Graphics g)
    {
        super.draw(g);
        // Schildsymbol
        g.setColor(new Color(210, 210, 210));
        g.drawOval((int)x - 7, (int)y - 7, 14, 14);
        g.drawOval((int)x - 4, (int)y - 4, 8, 8);
    }
}