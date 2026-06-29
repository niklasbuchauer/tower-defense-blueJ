import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Font;

public class FloatingText {
    private double x, y;
    private String text;
    private Color color;
    private int alpha = 255;      // Sichtbarkeit (255 = voll sichtbar, 0 = unsichtbar)
    private int lifetime = 45;    // Wie viele Frames (Ticks) die Zahl lebt
    private double speedY = -0.8; // Geschwindigkeit, mit der die Zahl nach oben schwebt

    public FloatingText(double x, double y, String text, Color color) {
        this.x = x;
        this.y = y - 15; 
        this.text = text;
        this.color = color;
    }

    public void update() {
        y += speedY;
        lifetime--;
        
        if (lifetime < 20) {
            alpha = Math.max(0, alpha - 13);
        }
    }

    public void draw(Graphics g) {
        if (alpha <= 0) return;
        
        // 1. Original-Schriftart sichern
        Font originalFont = g.getFont();
        
        // 2. Text-Glättung aktivieren
        if (g instanceof Graphics2D) {
            Graphics2D g2d = (Graphics2D) g;
            g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        }
        
        // Extra fette Arial-Schriftart
        g.setFont(new Font("Arial", Font.BOLD, 17)); 
        
        // 3. SCHLAGSCHATTEN / KONTUR ZEICHNEN (Macht die Schrift optisch viel dicker und extrem lesbar)
        g.setColor(new Color(0, 0, 0, alpha)); // Schwarzer Schatten mit passendem Alpha
        g.drawString(text, (int)x + 1, (int)y + 1);
        g.drawString(text, (int)x - 1, (int)y - 1);
        g.drawString(text, (int)x + 1, (int)y - 1);
        g.drawString(text, (int)x - 1, (int)y + 1);
        
        // 4. Haupttext im Vordergrund zeichnen
        g.setColor(new Color(color.getRed(), color.getGreen(), color.getBlue(), alpha));
        g.drawString(text, (int)x, (int)y);
        
        // 5. Original-Schriftart wiederherstellen
        g.setFont(originalFont);
    }

    public boolean isDead() {
        return lifetime <= 0;
    }
}