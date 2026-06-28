import java.awt.Color;
import java.awt.Point;
import java.util.ArrayList;

public class DemonBoss extends BossEnemy
{
    public DemonBoss(ArrayList<Point> path, int wave)
    {
        super(path, wave);
        health    = 4000 + wave * 160;
        maxHealth = health;
        speed     = 0.8;
        baseSpeed = 0.8;
        reward    = 380 + wave * 22;
    }

    public void update(ArrayList<Point> path)
    {
        super.update(path);
        // Wird schneller bei niedrigen HP
        double hpPercent = (double) health / maxHealth;
        if (hpPercent < 0.5)
            speed = baseSpeed * 1.5;
        else if (hpPercent < 0.25)
            speed = baseSpeed * 2.2;
        else
            speed = baseSpeed;
    }

    public Color getColor() { return new Color(160, 20, 20); }
}