import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Composite;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.util.ArrayList;

public class ShadowBoss extends BossEnemy
{
    private int stealthTimer = 0;
    private boolean invisible = false;

    public ShadowBoss(ArrayList<Point> path, int wave)
    {
        super(path, wave);
        health    = 1500 + wave * 110;
        maxHealth = health;
        speed     = 1.0;
        baseSpeed = 1.0;
        reward    = 180 + wave * 14;
    }

    public void update(ArrayList<Point> path)
    {
        super.update(path);
        stealthTimer++;
        // Alle 5 Sekunden (300 Frames) wechseln
        if (stealthTimer >= 300)
        {
            stealthTimer = 0;
            invisible = !invisible;
            stealth   = invisible;
        }
    }

    public Color getColor() { return Color.BLACK; }

    public void draw(Graphics g)
    {
        if (invisible)
        {
            Graphics2D g2 = (Graphics2D) g;
            Composite old = g2.getComposite();
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.3f));
            super.draw(g);
            g2.setComposite(old);
        }
        else
        {
            super.draw(g);
        }
    }
}