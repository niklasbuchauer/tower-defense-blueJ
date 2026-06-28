import java.awt.Color;
import java.awt.Point;
import java.util.ArrayList;

public class NecromancerBoss extends BossEnemy
{
    private int summonTimer = 0;
    private ArrayList<Enemy> enemyList;

    public NecromancerBoss(ArrayList<Point> path, int wave, ArrayList<Enemy> enemyList)
    {
        super(path, wave);
        health         = 7000 + wave * 220;
        maxHealth      = health;
        speed          = 0.7;
        baseSpeed      = 0.7;
        reward         = 600 + wave * 30;
        this.enemyList = enemyList;
    }

    public void update(ArrayList<Point> path)
    {
        super.update(path);
        summonTimer++;
        if (summonTimer >= 300)
        {
            summonTimer = 0;
            for (int i = 0; i < 2; i++)
                enemyList.add(new SplitterEnemy(path, 80, false));
        }
    }

    public Color getColor() { return new Color(80, 0, 120); }
}