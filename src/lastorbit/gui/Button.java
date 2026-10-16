package lastorbit.gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

public class Button {

    private final int x;
    private final int y;
    private final int width;
    private final int height;
    private final String label;

    public Button(int x, int y, int width, int height, String label) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.label = label;
    }

    public boolean contains(int px, int py) {
        return px >= x && px <= x + width && py >= y && py <= y + height;
    }

    public void render(Graphics2D g, boolean hovered) {
        g.setColor(hovered ? new Color(40, 90, 140) : new Color(25, 55, 90));
        g.fillRoundRect(x, y, width, height, 12, 12);
        g.setColor(new Color(120, 200, 255));
        g.drawRoundRect(x, y, width, height, 12, 12);
        g.setFont(new Font("Consolas", Font.BOLD, 22));
        int textWidth = g.getFontMetrics().stringWidth(label);
        g.setColor(Color.WHITE);
        g.drawString(label, x + (width - textWidth) / 2, y + height / 2 + 8);
    }

    public int getX() { return x; }
    public int getY() { return y; }
    public int getWidth() { return width; }
    public int getHeight() { return height; }
}