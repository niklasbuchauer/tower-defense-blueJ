import java.awt.Point;
import java.util.ArrayList;

public class WaveManager
{
    private int currentWave;
    private int spawnTimer;
    private int spawnIndex;
    private int groupIndex;

    // Format: { TYP, ANZAHL, DELAY }
    // TYP: 0=Normal 1=Fast 2=Tank 3=Splitter 4=Stealth 5=Regen
    //      6=Armored 7=Ice 8=Healer 9-18=Bosse (siehe spawnEnemy)
    private static final int[][][] WAVES = {
        // W1
        {{ 0, 6, 40 }},
        // W2
        {{ 0, 8, 35 }, { 1, 3, 40 }},
        // W3
        {{ 1, 8, 30 }},
        // W4
        {{ 0, 8, 28 }, { 2, 2, 60 }},
        // W5
        {{ 3, 4, 38 }},
        // W6
        {{ 1, 10, 25 }, { 3, 3, 38 }},
        // W7
        {{ 0, 12, 22 }, { 2, 3, 55 }},
        // W8
        {{ 1, 8, 22 }, { 3, 5, 35 }},
        // W9
        {{ 0, 15, 18 }, { 1, 6, 22 }, { 2, 2, 55 }},
        // W10 – BOSS: Wächter
        {{ 9, 1, 0 }, { 0, 6, 25 }},
        // W11
        {{ 4, 6, 30 }},
        // W12
        {{ 5, 8, 30 }},
        // W13
        {{ 4, 6, 25 }, { 1, 8, 22 }},
        // W14
        {{ 6, 4, 50 }},
        // W15
        {{ 5, 8, 25 }, { 4, 5, 28 }},
        // W16
        {{ 0, 18, 16 }, { 6, 3, 50 }},
        // W17
        {{ 4, 8, 20 }, { 1, 8, 20 }},
        // W18
        {{ 5, 8, 22 }, { 2, 3, 52 }},
        // W19
        {{ 4, 8, 20 }, { 5, 6, 24 }, { 1, 6, 20 }},
        // W20 – BOSS: Gepanzert
        {{ 10, 1, 0 }, { 6, 4, 45 }, { 0, 8, 20 }},
        // W21
        {{ 7, 5, 35 }},
        // W22
        {{ 8, 3, 60 }, { 2, 4, 52 }},
        // W23
        {{ 7, 4, 32 }, { 6, 4, 45 }},
        // W24
        {{ 8, 3, 55 }, { 5, 6, 26 }, { 2, 4, 50 }},
        // W25
        {{ 0, 22, 14 }, { 8, 3, 58 }},
        // W26
        {{ 4, 10, 18 }, { 7, 5, 30 }},
        // W27
        {{ 5, 10, 20 }, { 8, 3, 55 }},
        // W28
        {{ 6, 6, 38 }, { 7, 5, 30 }, { 2, 3, 52 }},
        // W29
        {{ 4, 10, 16 }, { 5, 8, 20 }, { 8, 2, 56 }},
        // W30 – BOSS: Schatten
        {{ 11, 1, 0 }, { 4, 6, 20 }, { 5, 5, 25 }},
        // W31
        {{ 4, 12, 16 }, { 1, 10, 18 }},
        // W32
        {{ 6, 8, 36 }, { 5, 8, 20 }},
        // W33
        {{ 3, 8, 28 }, { 7, 6, 30 }},
        // W34
        {{ 4, 12, 15 }, { 6, 6, 36 }},
        // W35
        {{ 0, 28, 12 }, { 8, 4, 52 }},
        // W36
        {{ 5, 10, 18 }, { 2, 5, 48 }, { 4, 6, 18 }},
        // W37
        {{ 7, 8, 27 }, { 6, 6, 36 }},
        // W38
        {{ 3, 10, 26 }, { 5, 8, 20 }},
        // W39
        {{ 4, 12, 14 }, { 1, 12, 15 }},
        // W40 – BOSS: Frostlord
        {{ 12, 1, 0 }, { 7, 6, 27 }, { 6, 5, 36 }},
        // W41
        {{ 0, 32, 10 }, { 4, 8, 17 }},
        // W42
        {{ 5, 12, 17 }, { 6, 6, 34 }},
        // W43
        {{ 2, 8, 44 }, { 8, 4, 50 }, { 3, 6, 27 }},
        // W44
        {{ 4, 12, 15 }, { 7, 6, 27 }},
        // W45
        {{ 0, 36, 10 }, { 5, 10, 17 }},
        // W46
        {{ 6, 8, 32 }, { 4, 10, 14 }, { 8, 3, 50 }},
        // W47
        {{ 3, 10, 24 }, { 7, 8, 25 }},
        // W48
        {{ 5, 12, 15 }, { 4, 10, 15 }, { 1, 12, 14 }},
        // W49
        {{ 2, 8, 40 }, { 6, 8, 31 }, { 8, 4, 48 }},
        // W50 – BOSS: König
        {{ 13, 1, 0 }, { 0, 15, 12 }, { 4, 8, 17 }},
        // W51
        {{ 0, 40, 8 }, { 5, 10, 15 }},
        // W52
        {{ 4, 15, 13 }, { 6, 8, 29 }},
        // W53
        {{ 3, 12, 22 }, { 8, 5, 46 }, { 7, 8, 24 }},
        // W54
        {{ 5, 15, 13 }, { 4, 12, 13 }},
        // W55
        {{ 2, 10, 36 }, { 6, 10, 28 }, { 0, 20, 10 }},
        // W56
        {{ 4, 15, 12 }, { 7, 8, 23 }},
        // W57
        {{ 5, 15, 12 }, { 3, 10, 21 }, { 8, 4, 46 }},
        // W58
        {{ 6, 12, 25 }, { 4, 15, 12 }, { 1, 15, 13 }},
        // W59
        {{ 5, 18, 11 }, { 7, 10, 22 }, { 8, 5, 44 }},
        // W60 – BOSS: Dämon
        {{ 14, 1, 0 }, { 4, 12, 12 }, { 5, 12, 13 }},
        // W61
        {{ 0, 50, 7 }, { 6, 10, 24 }},
        // W62
        {{ 4, 18, 10 }, { 2, 8, 34 }},
        // W63
        {{ 3, 15, 19 }, { 8, 6, 42 }, { 5, 12, 12 }},
        // W64
        {{ 6, 12, 23 }, { 7, 10, 22 }, { 4, 12, 11 }},
        // W65
        {{ 5, 20, 10 }, { 0, 30, 8 }},
        // W66
        {{ 4, 20, 9 }, { 8, 6, 40 }},
        // W67
        {{ 2, 10, 32 }, { 6, 12, 22 }, { 3, 12, 19 }},
        // W68
        {{ 5, 20, 10 }, { 4, 15, 10 }, { 7, 10, 21 }},
        // W69
        {{ 8, 6, 40 }, { 5, 18, 10 }, { 3, 12, 18 }},
        // W70 – BOSS: Titan
        {{ 15, 1, 0 }, { 6, 12, 21 }, { 5, 15, 10 }, { 4, 10, 10 }},
        // W71
        {{ 0, 60, 6 }, { 5, 15, 9 }},
        // W72
        {{ 4, 20, 8 }, { 6, 12, 20 }},
        // W73
        {{ 3, 18, 16 }, { 8, 7, 38 }, { 7, 12, 19 }},
        // W74
        {{ 5, 22, 8 }, { 4, 18, 8 }, { 2, 8, 30 }},
        // W75
        {{ 6, 15, 19 }, { 0, 40, 7 }, { 8, 6, 38 }},
        // W76 – Splitter-Schwall
        {{ 5, 30, 7 }, { 3, 20, 15 }},
        // W77
        {{ 4, 22, 7 }, { 7, 12, 18 }, { 8, 6, 38 }},
        // W78
        {{ 6, 15, 18 }, { 5, 22, 8 }, { 3, 15, 15 }},
        // W79
        {{ 4, 25, 7 }, { 2, 10, 28 }, { 8, 7, 36 }},
        // W80 – BOSS: Nekromant
        {{ 16, 1, 0 }, { 3, 15, 15 }, { 5, 15, 8 }, { 4, 12, 8 }},
        // W81
        {{ 0, 70, 5 }, { 4, 20, 7 }, { 5, 15, 8 }},
        // W82
        {{ 6, 18, 16 }, { 7, 15, 16 }, { 4, 20, 7 }},
        // W83
        {{ 3, 20, 13 }, { 8, 8, 34 }, { 5, 20, 7 }},
        // W84
        {{ 4, 25, 6 }, { 6, 18, 16 }, { 2, 10, 26 }},
        // W85
        {{ 0, 80, 5 }, { 5, 20, 7 }, { 8, 8, 33 }},
        // W86
        {{ 4, 28, 5 }, { 7, 15, 15 }, { 3, 18, 13 }},
        // W87
        {{ 6, 20, 15 }, { 5, 25, 6 }, { 8, 8, 32 }},
        // W88
        {{ 4, 30, 5 }, { 2, 12, 24 }, { 3, 18, 12 }},
        // W89
        {{ 5, 28, 5 }, { 4, 25, 5 }, { 6, 20, 14 }, { 8, 8, 31 }},
        // W90 – BOSS: Kaiser
        {{ 17, 1, 0 }, { 4, 20, 6 }, { 5, 20, 6 }, { 6, 15, 15 }, { 8, 8, 30 }},
        // W91
        {{ 0, 90, 4 }, { 4, 25, 5 }, { 5, 22, 6 }},
        // W92
        {{ 6, 22, 13 }, { 7, 18, 14 }, { 3, 22, 11 }, { 8, 8, 29 }},
        // W93
        {{ 4, 30, 5 }, { 5, 25, 5 }, { 2, 12, 22 }},
        // W94
        {{ 0, 100, 4 }, { 6, 22, 12 }, { 8, 10, 28 }},
        // W95
        {{ 4, 35, 4 }, { 5, 30, 5 }, { 3, 22, 10 }, { 7, 18, 13 }},
        // W96
        {{ 6, 25, 11 }, { 2, 15, 20 }, { 4, 30, 4 }, { 8, 10, 27 }},
        // W97
        {{ 0, 120, 3 }, { 5, 35, 4 }, { 4, 30, 4 }},
        // W98
        {{ 6, 28, 10 }, { 3, 25, 9 }, { 5, 35, 4 }, { 4, 30, 4 }},
        // W99
        {{ 0, 60, 4 }, { 4, 35, 4 }, { 5, 35, 4 }, { 6, 25, 10 },
           { 7, 20, 12 }, { 8, 10, 26 }, { 3, 25, 9 }},
        // W100 – ENDKÖNIG
        {{ 18, 1, 0 }, { 0, 50, 4 }, { 4, 40, 4 }, { 5, 40, 4 },
           { 6, 30, 9 }, { 3, 30, 8 }, { 8, 12, 24 }, { 2, 15, 18 }}
    };

    private ArrayList<Enemy> enemiesRef;

    public WaveManager()
    {
        currentWave = 1;
        spawnIndex  = 0;
        groupIndex  = 0;
        spawnTimer  = 0;
    }

    public int getWave()       { return currentWave; }
    public int getTotalWaves() { return WAVES.length; }

    public void update(ArrayList<Enemy> enemies, ArrayList<Point> path)
    {
        this.enemiesRef = enemies;
        int wi = currentWave - 1;
        if (wi >= WAVES.length) return;

        int[][] wave = WAVES[wi];

        if (groupIndex >= wave.length)
        {
            if (enemies.isEmpty())
            {
                currentWave++;
                groupIndex = 0;
                spawnIndex = 0;
                spawnTimer = 120;
            }
            return;
        }

        if (spawnTimer > 0) { spawnTimer--; return; }

        int[] group  = wave[groupIndex];
        int type     = group[0];
        int count    = group[1];
        int delay    = group[2];

        spawnEnemy(enemies, path, type);
        spawnIndex++;

        if (spawnIndex >= count)
        {
            spawnIndex = 0;
            groupIndex++;
            spawnTimer = 80; // Pause zwischen Gruppen
        }
        else
        {
            spawnTimer = delay;
        }
    }

    private void spawnEnemy(ArrayList<Enemy> enemies, ArrayList<Point> path, int type)
    {
        int w = currentWave;
        switch (type)
        {
            case 0:  enemies.add(new NormalEnemy(path, w));                          break;
            case 1:  enemies.add(new FastEnemy(path, w));                            break;
            case 2:  enemies.add(new TankEnemy(path, w));                            break;
            case 3:  enemies.add(new SplitterEnemy(path, w, false));                 break;
            case 4:  enemies.add(new StealthEnemy(path, w));                         break;
            case 5:  enemies.add(new RegenEnemy(path, w));                           break;
            case 6:  enemies.add(new ArmoredEnemy(path, w));                         break;
            case 7:  enemies.add(new IceEnemy(path, w));                             break;
            case 8:  enemies.add(new HealerEnemy(path, w));                          break;
            case 9:  enemies.add(new WatcherBoss(path, w));                          break;
            case 10: enemies.add(new ArmorBoss(path, w));                            break;
            case 11: enemies.add(new ShadowBoss(path, w));                           break;
            case 12: enemies.add(new FrostlordBoss(path, w));                        break;
            case 13: enemies.add(new KingBoss(path, w, enemies));                    break;
            case 14: enemies.add(new DemonBoss(path, w));                            break;
            case 15: enemies.add(new TitanBoss(path, w));                            break;
            case 16: enemies.add(new NecromancerBoss(path, w, enemies));             break;
            case 17: enemies.add(new EmperorBoss(path, w, enemies));                 break;
            case 18: enemies.add(new EndKingBoss(path, w, enemies));                 break;
        }
    }

    public boolean victory()
    {
        return currentWave > WAVES.length;
    }
}