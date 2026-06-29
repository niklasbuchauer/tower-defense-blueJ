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

    private int selectedTower; // 0 = Kein Turm ausgewählt, 1-4 = Turmtyp aktiv
    
    // Listen und Einstellungen für visuelle Effekte
    private java.util.List<FloatingText> floatingTexts = new java.util.ArrayList<>();
    public boolean showDamageNumbers = true; // Schalter für die Einstellungen (true = an, false = aus)
    
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

        selectedTower = 0; // Startet ohne Auswahl
        
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
                
                if (e.getKeyCode() == KeyEvent.VK_ESCAPE) selectedTower = 0;

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
        if (e.getButton() == MouseEvent.BUTTON3)
        {
            selectedTower = 0; 
            infoPanel.deselect();
            return;
        }

        if (infoPanel.isVisible() && infoPanel.isUpgradeButtonClick(x, y))
        {
            Tower t = infoPanel.getSelected();
            int cost = t.getUpgradeCost();
            if (player.spendMoney(cost))
                t.upgrade();
            return;
        }

        Tower clicked = getTowerAt(x, y);
        if (clicked != null)
        {
            infoPanel.select(clicked);
            return;
        }

        infoPanel.deselect();

        if (selectedTower == 0)
            return;

        if (!map.canBuild(x, y))
            return;

        if (isTowerAt(x, y))
            return;
        
        if (countTowersOfType(selectedTower) >= 5)
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
            case 1: return 60; // Angepasst an die Menüanzeige (60$)
            case 2: return 100; // Angepasst an die Menüanzeige (100$)
            case 3: return 80;  // Angepasst an die Menüanzeige (80$)
            case 4: return 90;  // Angepasst an die Menüanzeige (90$)
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

        for (Enemy e : enemies)
        {
            e.update(map.getPath());

            if (e.reachedEnd(map.getPath()) && !e.isDead())
            {
                player.loseLife(1);
                e.setReward(0);
                e.takeDamage(9999);
            }
        }

        for (Tower t : towers)
        {
            t.update(enemies, bullets);
        }

        for (Bullet b : bullets)
        {
            b.update(this);
        }

        for (int i = floatingTexts.size() - 1; i >= 0; i--)
        {
            FloatingText ft = floatingTexts.get(i);
            ft.update();
            if (ft.isDead())
            {
                floatingTexts.remove(i);
            }
        }

        for (Enemy e : enemies)
        {
            if (e.isDead() && e.getReward() > 0)
            {
                player.addMoney(e.getReward());
                e.setReward(0);
            }
        }

        ArrayList<Enemy> shards = new ArrayList<>();
        for (Enemy e : enemies)
        {
            if (e instanceof SplitterEnemy && e.isDead())
            {
                SplitterEnemy sp = (SplitterEnemy) e;
                if (!sp.isShard() && !sp.hasSpawnedShards())  
                {
                    sp.markShardsSpawned();                    
                    for (int i = 0; i < 3; i++)
                        shards.add(new SplitterEnemy(map.getPath(), waveManager.getWave(), true));
                }
            }
        }
        enemies.addAll(shards);
        
        enemies.removeIf(e -> e.isDead());

        for (Enemy e : enemies)
        {
            if (e instanceof HealerEnemy)
                ((HealerEnemy) e).updateHealer(enemies, map.getPath());
        }

        boolean icePresent = false;
        for (Enemy e : enemies)
        {
            if (e instanceof IceEnemy && !e.isDead()) { icePresent = true; break; }
        }
        for (Tower t : towers)
        {
            if (icePresent)
                t.applyIceSlow();
            else
                t.removeIceSlow();
        }
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

            for (Tower t : new ArrayList<>(towers)) t.draw(g);
            for (Enemy e : new ArrayList<>(enemies)) e.draw(g);
            for (Bullet b : new ArrayList<>(bullets)) b.draw(g);

            for (FloatingText ft : floatingTexts)
            {
                ft.draw(g);
            }

            // REICHWEITEN-KREIS UND PREISANZEIGE AN DER MAUS
            if (selectedTower != 0) 
            {
                java.awt.Point mousePoint = getMousePosition();
                if (mousePoint != null) 
                {
                    int mouseX = mousePoint.x;
                    int mouseY = mousePoint.y;
                    
                    int currentRange = 100; 
                    String infoText = "";
                    
                    if (selectedTower == 1) { currentRange = 120; infoText = "Basic: 60$"; }
                    if (selectedTower == 2) { currentRange = 250; infoText = "Sniper: 100$"; }
                    if (selectedTower == 3) { currentRange = 90;  infoText = "Rapid: 80$"; }
                    if (selectedTower == 4) { currentRange = 130; infoText = "Freeze: 90$"; }
                    
                    // 1. Reichweitenkreis zeichnen
                    g.setColor(new Color(0, 150, 255, 40)); 
                    g.fillOval(mouseX - currentRange, mouseY - currentRange, currentRange * 2, currentRange * 2);
                    g.setColor(new Color(0, 150, 255, 120));
                    g.drawOval(mouseX - currentRange, mouseY - currentRange, currentRange * 2, currentRange * 2);
                    
                    // 2. Kostenanzeige neben dem Mauszeiger zeichnen
                    Font prevFont = g.getFont();
                    g.setFont(new Font("Arial", Font.BOLD, 14));
                    
                    // Schatten für Lesbarkeit der Kosten
                    g.setColor(Color.BLACK);
                    g.drawString(infoText, mouseX + 16, mouseY + 6);
                    
                    // Farb-Indikator: Rot wenn zu teuer, Grün wenn bezahlbar
                    if (player.getMoney() >= getTowerCost()) {
                        g.setColor(new Color(0, 200, 0)); // Schönes Grün
                    } else {
                        g.setColor(Color.RED);
                    }
                    g.drawString(infoText, mouseX + 15, mouseY + 5);
                    g.setFont(prevFont);
                }
            }

            hud.draw(g, player, waveManager, selectedTower);
            if (infoPanel != null && infoPanel.isVisible())
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

    private int countTowersOfType(int type)
    {
        int count = 0;
        for (Tower t : towers)
        {
            switch (type)
            {
                case 1: if (t instanceof BasicTower)  count++; break;
                case 2: if (t instanceof SniperTower) count++; break;
                case 3: if (t instanceof RapidTower)  count++; break;
                case 4: if (t instanceof FreezeTower) count++; break;
            }
        }
        return count;
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
        g.drawString("Linksklick = Tower setzen        Rechtsklick = Auswahl aufheben / Menue schliessen", 140, 365);
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
    
    public void addFloatingText(double x, double y, String text, Color color) {
        if (showDamageNumbers) {
            floatingTexts.add(new FloatingText(x, y, text, color));
        }
    }
}