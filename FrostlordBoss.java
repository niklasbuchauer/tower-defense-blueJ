import java.awt.Color;
import java.awt.Point;
import java.util.ArrayList;

public class FrostlordBoss extends BossEnemy
{
    public FrostlordBoss(ArrayList<Point> path, int wave)
    {
        super(path, wave);
        health    = 2000 + wave * 120;
        maxHealth = health;
        speed     = 0.9;
        baseSpeed = 0.9;
        reward    = 220 + wave * 16;
    }

    // Freeze-immun
    public void applyFreeze(int duration) { /* ignorieren */ }

    public Color getColor() { return new Color(100, 180, 255); }
}