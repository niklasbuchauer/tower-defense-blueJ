import java.awt.Color;
import java.awt.Point;
import java.util.ArrayList;

public class EndKingBoss extends BossEnemy
{
    private int timer = 0;
    private int phase = 1;
    private ArrayList<Enemy> enemyList;
    private ArrayList<Point> pathRef;

    public EndKingBoss(ArrayList<Point> path, int wave, ArrayList<Enemy> enemyList)
    {
        super(path, wave);
        health         = 25000;
        maxHealth      = health;
        speed          = 0.8;
        baseSpeed      = 0.8;
        reward         = 2000;
        this.enemyList = enemyList;
        this.pathRef   = path;
    }

    public void takeDamage(int damage)
    {
        super.takeDamage(Math.max(1, damage - phase * 5));
    }

    public void update(ArrayList<Point> path)
    {
        super.update(path);
        timer++;

        double hpPct = (double) health / maxHealth;

        // Phasenwechsel
        if (hpPct < 0.66 && phase == 1) { phase = 2; speed = baseSpeed * 1.3; }
        if (hpPct < 0.33 && phase == 2) { phase = 3; speed = baseSpeed * 1.8; stealth = true; }

        // Alle 4 Sekunden beschwören
        if (timer % 240 == 0)
        {
            enemyList.add(new NormalEnemy(pathRef, 100));
            enemyList.add(new StealthEnemy(pathRef, 100));
            if (phase >= 2) enemyList.add(new SplitterEnemy(pathRef, 100, false));
            if (phase >= 3) enemyList.add(new RegenEnemy(pathRef, 100));
        }

        // Freeze-immun
    }

    public void applyFreeze(int d) { /* ignorieren */ }

    public Color getColor()
    {
        return phase == 3 ? new Color(80, 0, 0)
             : phase == 2 ? new Color(40, 0, 40)
             : Color.BLACK;
    }
}