import java.awt.Color;
import java.awt.Point;
import java.util.ArrayList;

public class TitanBoss extends BossEnemy
{
    public TitanBoss(ArrayList<Point> path, int wave)
    {
        super(path, wave);
        health    = 6000 + wave * 200;
        maxHealth = health;
        speed     = 0.5;
        baseSpeed = 0.5;
        reward    = 500 + wave * 28;
    }

    public void takeDamage(int damage)
    {
        super.takeDamage(Math.max(1, damage - 15)); // hohe Rüstung
    }

    public Color getColor() { return new Color(120, 120, 120); }
}