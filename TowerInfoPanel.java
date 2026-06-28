import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

public class TowerInfoPanel
{
    private Tower selectedTower;

    private static final int PX      = 280;  // Panel X
    private static final int PY      = 430;  // Panel Y
    private static final int PW      = 240;  // Panel Breite
    private static final int PH      = 155;  // Panel Hoehe

    // Upgrade-Button Bereich
    public static final int BTN_X = PX + 10;
    public static final int BTN_Y = PY + 115;
    public static final int BTN_W = PW - 20;
    public static final int BTN_H = 28;

    public void select(Tower t)
    {
        selectedTower = t;
    }

    public void deselect()
    {
        selectedTower = null;
    }

    public Tower getSelected()
    {
        return selectedTower;
    }

    public boolean isVisible()
    {
        return selectedTower != null;
    }

    public boolean isUpgradeButtonClick(int x, int y)
    {
        return x >= BTN_X && x <= BTN_X + BTN_W
            && y >= BTN_Y && y <= BTN_Y + BTN_H;
    }

    public void draw(Graphics g, Player player)
    {
        if (selectedTower == null) return;

        // Hintergrund
        g.setColor(new Color(30, 30, 30, 210));
        g.fillRoundRect(PX, PY, PW, PH, 12, 12);

        // Rahmen
        g.setColor(new Color(200, 170, 80));
        g.drawRoundRect(PX, PY, PW, PH, 12, 12);

        // Titel
        g.setFont(new Font("Arial", Font.BOLD, 14));
        g.setColor(new Color(255, 220, 80));
        g.drawString(selectedTower.getName() + "  [Lvl " + selectedTower.getLevel() + "]", PX + 10, PY + 20);

        // Trennlinie
        g.setColor(new Color(200, 170, 80));
        g.drawLine(PX + 10, PY + 27, PX + PW - 10, PY + 27);

        // Stats
        g.setFont(new Font("Arial", Font.PLAIN, 12));
        g.setColor(Color.WHITE);

        int lh = 18; // Zeilenhoehe
        int tx = PX + 10;
        int ty = PY + 45;

        g.drawString("Schaden:      " + selectedTower.getDamage(), tx, ty);
        g.drawString("Reichweite:   " + selectedTower.getRange(),  tx, ty + lh);
        g.drawString("Feuerrate:    " + getFireRateLabel(),        tx, ty + lh * 2);
        g.drawString("Gesamt-DMG:   " + selectedTower.getTotalDamageDealt(), tx, ty + lh * 3);

        // Upgrade-Infos
        g.setColor(new Color(150, 220, 255));
        int cost = selectedTower.getUpgradeCost();
        g.drawString("Upgrade: " + cost + "$  ->  +" + getUpgradePreview(), tx, ty + lh * 4);

        // Upgrade-Button
        if (selectedTower.getLevel() >= 5)
        {
            g.setColor(new Color(80, 80, 80));
            g.fillRoundRect(BTN_X, BTN_Y, BTN_W, BTN_H, 8, 8);
            g.setColor(Color.WHITE);
            g.setFont(new Font("Arial", Font.BOLD, 12));
            g.drawString("MAX LEVEL erreicht!", BTN_X + 25, BTN_Y + 18);
        }
        else
        {
            boolean canAfford = player.getMoney() >= cost;  // kein "int cost" nochmal!
            g.setColor(canAfford ? new Color(60, 160, 60) : new Color(120, 50, 50));
            g.fillRoundRect(BTN_X, BTN_Y, BTN_W, BTN_H, 8, 8);
            g.setColor(Color.WHITE);
            g.setFont(new Font("Arial", Font.BOLD, 12));
            String btnText = canAfford ? "Upgraden  (" + cost + "$)" : "Kein Geld  (" + cost + "$)";
            g.drawString(btnText, BTN_X + 18, BTN_Y + 18);
        }
    }

    private String getFireRateLabel()
    {
        int fr = selectedTower.getFireRate();
        if (fr <= 10)  return "Sehr schnell (" + fr + ")";
        if (fr <= 25)  return "Schnell (" + fr + ")";
        if (fr <= 50)  return "Normal (" + fr + ")";
        return "Langsam (" + fr + ")";
    }

    private String getUpgradePreview()
    {
        return selectedTower.getUpgradeDescription();
    }
}