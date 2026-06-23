import java.awt.Point;
import java.util.ArrayList;

public class WaveManager
{
    private int currentWave;

    private int enemiesToSpawn;
    private int spawnTimer;

    public WaveManager()
    {
        currentWave = 1;
        enemiesToSpawn = 10;
        spawnTimer = 0;
    }

    public int getWave()
    {
        return currentWave;
    }

    public void update(ArrayList<Enemy> enemies, ArrayList<Point> path)
    {
        if (enemiesToSpawn <= 0)
        {
            if (enemies.isEmpty())
            {
                currentWave++;

                if (currentWave <= 10)
                {
                    enemiesToSpawn = 8 + currentWave * 3;
                }
            }

            return;
        }

        if (spawnTimer > 0)
        {
            spawnTimer--;
            return;
        }

        spawnEnemy(enemies, path);

        enemiesToSpawn--;
        spawnTimer = 40;
    }

    private void spawnEnemy(ArrayList<Enemy> enemies, ArrayList<Point> path)
    {
        if (currentWave % 10 == 0)
        {
            enemies.add(new BossEnemy(path));
            return;
        }

        if (currentWave % 3 == 0)
        {
            enemies.add(new FastEnemy(path));
            return;
        }

        if (currentWave % 4 == 0)
        {
            enemies.add(new TankEnemy(path));
            return;
        }

        enemies.add(new Enemy(path, 80 + currentWave * 10, 2.0, 10));
    }

    public boolean victory()
    {
        return currentWave > 10;
    }
}