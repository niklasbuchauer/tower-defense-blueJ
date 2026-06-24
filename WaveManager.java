import java.awt.Point;
import java.util.ArrayList;

public class WaveManager
{
    private int currentWave;
    private int spawnTimer;
    private int spawnIndex;

    // 0=Normal  1=Fast  2=Tank  3=Boss
    // Wellen werden zunehmend größer und schwieriger
    private static final int[][] WAVES = {
        {0, 0, 0, 1, 1},                               // Welle 1:  5  Gegner
        {0, 0, 1, 1, 1, 0, 0},                         // Welle 2:  7  Gegner
        {0, 0, 0, 2, 1, 1, 0, 1},                      // Welle 3:  8  Gegner
        {1, 1, 1, 2, 2, 0, 0, 1, 0},                   // Welle 4:  9  Gegner
        {0, 1, 2, 1, 0, 2, 1, 1, 0, 0},                // Welle 5: 10  Gegner
        {1, 1, 1, 1, 2, 2, 2, 0, 1, 1, 0},             // Welle 6: 11  Gegner
        {3, 0, 1, 1, 0, 2, 1, 2, 1, 0, 1},             // Welle 7: 11  Gegner (1 Boss)
        {1, 1, 2, 2, 2, 1, 1, 0, 2, 1, 1, 0},          // Welle 8: 12  Gegner
        {2, 2, 1, 1, 1, 3, 0, 2, 1, 1, 2, 0, 1},       // Welle 9: 13  Gegner (1 Boss)
        {1, 1, 2, 2, 1, 3, 1, 2, 1, 1, 2, 1, 0, 2},   // Welle 10: 14 Gegner (1 Boss)
        {3, 1, 1, 2, 1, 2, 1, 1, 2, 2, 1, 1, 1, 0},   // Welle 11: 14 Gegner (1 Boss)
        {1, 2, 1, 3, 1, 2, 1, 1, 2, 1, 3, 1, 2, 1, 0},// Welle 12: 15 Gegner (2 Bosse)
        {2, 1, 1, 3, 2, 1, 1, 2, 1, 3, 1, 2, 1, 1, 2},// Welle 13: 15 Gegner (2 Bosse)
        {3, 2, 2, 1, 1, 3, 2, 2, 1, 1, 2, 1, 2, 1, 3, 1},        // Welle 14: 16 (3 Bosse)
        {3, 3, 1, 2, 1, 2, 3, 1, 2, 1, 3, 2, 1, 2, 1, 2, 1, 2}   // Welle 15: 18 (4 Bosse)
    };

    public WaveManager()
    {
        currentWave = 1;
        spawnIndex  = 0;
        spawnTimer  = 0;
    }

    public int getWave()
    {
        return currentWave;
    }

    public int getTotalWaves()
    {
        return WAVES.length;
    }

    public void update(ArrayList<Enemy> enemies, ArrayList<Point> path)
    {
        int wi = currentWave - 1;

        if (wi >= WAVES.length)
            return;

        int[] wave = WAVES[wi];

        if (spawnIndex >= wave.length)
        {
            if (enemies.isEmpty())
            {
                currentWave++;
                spawnIndex = 0;
                spawnTimer = 100; // Pause zwischen Wellen
            }
            return;
        }

        if (spawnTimer > 0)
        {
            spawnTimer--;
            return;
        }

        spawnEnemy(enemies, path, wave[spawnIndex]);
        spawnIndex++;
        spawnTimer = 50;
    }

    private void spawnEnemy(ArrayList<Enemy> enemies, ArrayList<Point> path, int type)
    {
        int w = currentWave;

        switch (type)
        {
            case 0: // Normal
                enemies.add(new Enemy(path, 55 + w * 10, 1.0 + w * 0.04, 12 + w * 2));
                break;
            case 1: // Fast
                enemies.add(new FastEnemy(path, w));
                break;
            case 2: // Tank
                enemies.add(new TankEnemy(path, w));
                break;
            case 3: // Boss
                enemies.add(new BossEnemy(path, w));
                break;
        }
    }

    public boolean victory()
    {
        return currentWave > WAVES.length;
    }
}
