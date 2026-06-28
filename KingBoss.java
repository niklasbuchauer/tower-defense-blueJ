import java.awt.Color;
import java.awt.Point;
import java.util.ArrayList;

public class KingBoss extends BossEnemy
{
    private int summonTimer = 0;
    private ArrayList<Enemy> enemyList;

    public KingBoss(ArrayList<Point> path, int wave, ArrayList<Enemy> enemyList)
    {
        super(path, wave);
        health         = 3000 + wave * 150;
        maxHealth      = health;
        speed          = 0.7;
        baseSpeed      = 0.7;
        reward         = 300 + wave * 20;
        this.enemyList = enemyList;
    }

    public void update(ArrayList<Point> path)
    {
        super.update(path);
        summonTimer++;
        if (summonTimer >= 360) // alle 6 Sekunden
        {
            summonTimer = 0;
            for (int i = 0; i < 3; i++)
                enemyList.add(new NormalEnemy(path, 50));
        }
    }

    public Color getColor() { return new Color(20, 20, 20); }
}