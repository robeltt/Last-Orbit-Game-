package lastorbit.components;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RadialGradientPaint;
import java.awt.geom.Point2D;


public class Shield {

    private int hitsRemaining;
    private double pulse;

    public Shield(int hits) {
        this.hitsRemaining = hits;
        this.pulse = 0;
    }

    public boolean isActive() {
        return hitsRemaining > 0;
    }

    public boolean absorbHit() {
        if (hitsRemaining > 0) {
            hitsRemaining--;
            return true;
        }
        return false;
    }

    public void update() {
        pulse += 0.15;
    }

    public void render(Graphics2D g, int x, int y, int w, int h) {
        int cx = x + w / 2;
        int cy = y + h / 2;
        int radius = (int) (Math.max(w, h) * 0.75
                     + Math.sin(pulse) * 4);   
        float[] dist = {0.0f, 0.7f, 0.92f, 1.0f};
        Color[] colors = {
            new Color(80, 180, 255, 25),    
            new Color(80, 180, 255, 40),
            new Color(120, 210, 255, 130),  
            new Color(160, 230, 255, 60)
        };
        RadialGradientPaint paint = new RadialGradientPaint(
            new Point2D.Float(cx, cy), radius, dist, colors);
        g.setPaint(paint);
        g.fillOval(cx - radius, cy - radius, radius * 2, radius * 2);
    }

    public int getHitsRemaining() {
        return hitsRemaining;
    }
}