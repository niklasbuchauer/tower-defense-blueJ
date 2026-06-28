import java.awt.Color;
import java.awt.Point;
import java.util.ArrayList;

public class EmperorBoss extends BossEnemy
{
    private int timer = 0;
    private ArrayList<Enemy> enemyList;

    public EmperorBoss(ArrayList<Point> path, int wave, ArrayList<Enemy> enemyList)
    {
        super(path, wave);
        health         = 10000 + wave * 250;
        maxHealth      = health;
        speed          = 0.9;
        baseSpeed      = 0.9;
        reward         = 800 + wave * 35;
        this.enemyList = enemyList;
        this.stealth   = true;
    }

    public void takeDamage(int damage)
    {
        super.takeDamage(Math.max(1, damage - 8));
    }

    public void update(ArrayList<Point> path)
    {
        super.update(path);
        timer++;

        // Getarnt alle 8 Sekunden
        if (timer % 480 == 0) stealth = !stealth;

        // Beschwört alle 5 Sekunden
        if (timer % 300 == 0)
        {
            enemyList.add(new StealthEnemy(path, 90));
            enemyList.add(new RegenEnemy(path, 90));
        }

        // Berserker unter 30% HP
        speed = (double) health / maxHealth < 0.3 ? baseSpeed * 1.8 : baseSpeed;
    }

    public Color getColor() { return new Color(10, 10, 10); }
}