import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
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

    private int selectedTower;
    
    private TowerInfoPanel infoPanel;

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
        
        infoPanel = new TowerInfoPanel();

        addMouseListener(new MouseAdapter()
        {
            public void mousePressed(MouseEvent e)
            {
                if (state == GameState.MENU)
                {
                    state = GameState.PLAYING;
                    return;
                }

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
    
    private boolean isTowerAt(int x, int y)
    {
        for (Tower t : towers)
        {
            double dx = t.getX() - x;
            double dy = t.getY() - y;

            if (Math.sqrt(dx * dx + dy * dy) < 30)
                return true;
        }
        return false;
    }

    private Tower getTowerAt(int x, int y)
    {
        for (Tower t : towers)
        {
            double dx = t.getX() - x;
            double dy = t.getY() - y;
            if (Math.sqrt(dx * dx + dy * dy) < 20)
                return t;
        }
        return null;
    }
    
    private void handleMouse(int x, int y, MouseEvent e)
    {
        // Rechtsklick = immer Upgrade-Menü schließen + altes Upgrade
        if (e.getButton() == MouseEvent.BUTTON3)
        {
            infoPanel.deselect();
            return;
        }

        // Upgrade-Button im Panel geklickt?
        if (infoPanel.isVisible() && infoPanel.isUpgradeButtonClick(x, y))
        {
            Tower t = infoPanel.getSelected();
            int cost = t.getUpgradeCost();
            if (player.spendMoney(cost))
                t.upgrade();
            return;
        }

        // Linksklick auf bestehenden Tower -> Panel öffnen
        Tower clicked = getTowerAt(x, y);
        if (clicked != null)
        {
            infoPanel.select(clicked);
            return;
        }

        // Klick ins Leere -> Panel schließen
        infoPanel.deselect();

        // Tower platzieren
        if (!map.canBuild(x, y))
            return;

        if (isTowerAt(x, y))
            return;

        int cost = getTowerCost();
        if (!player.spendMoney(cost))
            return;

        Tower t = null;
        switch (selectedTower)
        {
            case 1: t = new BasicTower(x, y);  break;
            case 2: t = new SniperTower(x, y); break;
            case 3: t = new RapidTower(x, y);  break;
            case 4: t = new FreezeTower(x, y); break;
        }
        towers.add(t);
    }

    private int getTowerCost()
    {
        switch (selectedTower)
        {
            case 1: return 50;
            case 2: return 120;
            case 3: return 85;
            case 4: return 75;
        }
        return 60;
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

        // 1. Gegner bewegen
        for (Enemy e : enemies)
        {
            e.update(map.getPath());

            // Ziel erreicht: Leben abziehen, Belohnung entfernen (kein Geld)
            if (e.reachedEnd(map.getPath()) && !e.isDead())
            {
                player.loseLife(1);
                e.setReward(0);
                e.takeDamage(9999);
            }
        }

        // 2. Türme schießen
        for (Tower t : towers)
        {
            t.update(enemies, bullets);
        }

        // 3. Bullets bewegen und Treffer verarbeiten
        for (Bullet b : bullets)
        {
            b.update();
        }

        // 4. JETZT erst Geld prüfen – nach Bullet-Schaden dieser Frame
        for (Enemy e : enemies)
        {
            if (e.isDead() && e.getReward() > 0)
            {
                player.addMoney(e.getReward());
                e.setReward(0);
            }
        }

        // 5. Tote Gegner und verbrauchte Bullets entfernen
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

            hud.draw(g, player, waveManager, selectedTower);
            if (infoPanel.isVisible())
                infoPanel.draw(g, player);
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
        g.setFont(new Font("Arial", Font.BOLD, 36));
        g.drawString("TOWER DEFENSE", 230, 230);

        g.setFont(new Font("Arial", Font.PLAIN, 18));
        g.drawString("ENTER oder Klick zum Starten", 250, 280);

        g.setFont(new Font("Arial", Font.PLAIN, 14));
        g.drawString("Taste 1 = Basic (60$)   2 = Sniper (100$)   3 = Rapid (80$)   4 = Freeze (90$)", 130, 340);
        g.drawString("Linksklick = Tower setzen        Rechtsklick = Tower upgraden", 180, 365);
    }

    private void drawGameOver(Graphics g)
    {
        g.setColor(Color.RED);
        g.setFont(new Font("Arial", Font.BOLD, 52));
        g.drawString("GAME OVER", 240, 270);
        g.setFont(new Font("Arial", Font.PLAIN, 18));
        g.setColor(Color.BLACK);
        g.drawString("Programm neu starten fuer Neustart", 265, 320);
    }

    private void drawVictory(Graphics g)
    {
        g.setColor(new Color(0, 150, 0));
        g.setFont(new Font("Arial", Font.BOLD, 52));
        g.drawString("SIEG!", 315, 270);
        g.setFont(new Font("Arial", Font.PLAIN, 18));
        g.setColor(Color.BLACK);
        g.drawString("Alle 10 Wellen besiegt!", 295, 320);
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
