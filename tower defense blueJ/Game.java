import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferStrategy;
import java.util.ArrayList;

import javax.swing.JFrame;

public class Game extends Canvas implements Runnable
{
    private JFrame frame;
    private boolean running;

    private GameState state;

    private Player player;
    private GameMap map;
    private WaveManager waveManager;
    private Hud hud;

    private ArrayList<Enemy> enemies;
    private ArrayList<Tower> towers;
    private ArrayList<Bullet> bullets;

    private int selectedTower; // 1-4

    public Game()
    {
        frame = new JFrame("Tower Defense");

        this.setPreferredSize(new Dimension(800, 600));
        frame.add(this);

        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.setResizable(false);

        init();
        start();
    }

    private void init()
    {
        state = GameState.MENU;

        player = new Player();
        map = new GameMap(MapType.EASY);
        waveManager = new WaveManager();
        hud = new Hud();

        enemies = new ArrayList<>();
        towers = new ArrayList<>();
        bullets = new ArrayList<>();
 
        selectedTower = 1;
 
        addMouseListener(new MouseAdapter()
        {
            public void mousePressed(MouseEvent e)
            {
                if (state != GameState.PLAYING)
                    return;

                handleMouse(e.getX(), e.getY(), e);
            }
        });

        addKeyListener(new KeyAdapter()
        {
            public void keyPressed(KeyEvent e)
            {
                if (e.getKeyCode() == KeyEvent.VK_1) selectedTower = 1;
                if (e.getKeyCode() == KeyEvent.VK_2) selectedTower = 2;
                if (e.getKeyCode() == KeyEvent.VK_3) selectedTower = 3;
                if (e.getKeyCode() == KeyEvent.VK_4) selectedTower = 4;

                if (e.getKeyCode() == KeyEvent.VK_ENTER)
                {
                    if (state == GameState.MENU)
                        state = GameState.PLAYING;
                }
            }
        });

        setFocusable(true);
    }

    private void handleMouse(int x, int y, MouseEvent e)
    {
        if (e.getButton() == MouseEvent.BUTTON3)
        {
            upgradeTower(x, y);
            return;
        }

        if (!map.canBuild(x, y))
            return;

        int cost = getTowerCost();

        if (!player.spendMoney(cost))
            return;

        Tower t = null;

        switch (selectedTower)
        {
            case 1: t = new BasicTower(x, y); break;
            case 2: t = new SniperTower(x, y); break;
            case 3: t = new RapidTower(x, y); break;
            case 4: t = new FreezeTower(x, y); break;
        }

        towers.add(t);
    }

    private int getTowerCost()
    {
        switch (selectedTower)
        {
            case 1: return 100;
            case 2: return 150;
            case 3: return 120;
            case 4: return 140;
    }
        return 100;
    }

    public synchronized void start()
    {
        running = true;
        new Thread(this).start();
    }

    public void run()
    {
        long lastTime = System.nanoTime();
        double nsPerTick = 1000000000.0 / 60.0;
        double delta = 0;

        while (running)
        {
            long now = System.nanoTime();
            delta += (now - lastTime) / nsPerTick;
            lastTime = now;

            while (delta >= 1)
            {
                update();
                delta--;
            }

            render();
        }
    }

    private void update()
    {
        if (state != GameState.PLAYING)
            return;

        waveManager.update(enemies, map.getPath());

        for (Enemy e : enemies)
        {
        e.update(map.getPath());
    
        if (e.reachedEnd(map.getPath()))
        {
            player.loseLife(1);
            e.takeDamage(9999);
        }

        if (e.isDead() && e.getReward() > 0)
        {
            player.addMoney(e.getReward());
            e.setReward(0); // verhindert mehrfaches Geld
        }
        }

        for (Tower t : towers)
        {
            t.update(enemies, bullets);
        }

        for (Bullet b : bullets)
        {
            b.update();
        }

        enemies.removeIf(e -> e.isDead());
        bullets.removeIf(b -> !b.isActive());

        if (player.getLives() <= 0)
        {
            state = GameState.GAME_OVER;
        }

        if (waveManager.victory())
        {
            state = GameState.VICTORY;
        }
    }

    private void render()
    {
        BufferStrategy bs = this.getBufferStrategy();

        if (bs == null)
        {
            this.createBufferStrategy(3);
            return;
        }

        Graphics g = bs.getDrawGraphics();

        g.setColor(Color.WHITE);
        g.fillRect(0, 0, 800, 600);

        if (state == GameState.MENU)
        {
            drawMenu(g);
        }
        else if (state == GameState.PLAYING)
        {
            map.draw(g);

            for (Tower t : towers) t.draw(g);
            for (Enemy e : enemies) e.draw(g);
            for (Bullet b : bullets) b.draw(g);

            hud.draw(g, player, waveManager);
        }
        else if (state == GameState.GAME_OVER)
        {
            drawGameOver(g);
        }
        else if (state == GameState.VICTORY)
        {
            drawVictory(g);
        }

        g.dispose();
        bs.show();
    }

    private void drawMenu(Graphics g)
    {
        g.setColor(Color.BLACK);
        g.drawString("TOWER DEFENSE", 350, 250);
        g.drawString("ENTER zum Starten", 350, 280);
    }

    private void drawGameOver(Graphics g)
    {
        g.setColor(Color.RED);
        g.drawString("GAME OVER", 370, 250);
    }

    private void drawVictory(Graphics g)
    {
        g.setColor(Color.GREEN);
        g.drawString("SIEG!", 380, 250);
    }
    
    private void upgradeTower(int x, int y)
    {
        for (Tower t : towers)
        {
            double dx = t.getX() - x;
            double dy = t.getY() - y;
    
            if (Math.sqrt(dx * dx + dy * dy) < 20)
            {
                int cost = t.getUpgradeCost();
    
                if (player.spendMoney(cost))
                {
                    t.upgrade();
                }

                break;
            }
        }
    }
}