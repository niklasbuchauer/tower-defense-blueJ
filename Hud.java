import java.awt.Color;
import java.awt.Graphics;

public class Hud
{
    public void draw(Graphics g, Player player, WaveManager waveManager)
    {
        g.setColor(Color.BLACK);

        g.drawString("Geld: " + player.getMoney(), 10, 20);
        g.drawString("Leben: " + player.getLives(), 10, 40);
        g.drawString("Welle: " + waveManager.getWave(), 10, 60);

        g.drawString("1 = Basic Tower (100)", 650, 20);
        g.drawString("2 = Sniper Tower (150)", 650, 40);
        g.drawString("3 = Rapid Tower (120)", 650, 60);
        g.drawString("4 = Freeze Tower (140)", 650, 80);
    }
    
    public void draw(Graphics g, Player player, WaveManager waveManager, int selected)
    {
        g.setColor(Color.BLACK);
    
        g.drawString("Geld: " + player.getMoney(), 10, 20);
        g.drawString("Leben: " + player.getLives(), 10, 40);
        g.drawString("Welle: " + waveManager.getWave(), 10, 60);

        g.drawString("1 Basic", 650, 20);
        g.drawString("2 Sniper", 650, 40);
        g.drawString("3 Rapid", 650, 60);
        g.drawString("4 Freeze", 650, 80);

        g.drawString("SELECTED: " + selected, 650, 120);
    }
}