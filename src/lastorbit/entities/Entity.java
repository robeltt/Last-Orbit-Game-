package lastorbit.entities;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import lastorbit.util.Vector2D;

public abstract class Entity {
    private Vector2D position;
    private final int width;
    private final int height;
    private boolean alive;

    public Entity(double x, double y, int width, int height) {
        this.position = new Vector2D(x, y);
        this.width = width;
        this.height = height;
        this.alive = true;
    }
    public double getX() { return position.getX(); }
    public double getY() { return position.getY(); }
    public int getWidth() { return width; }
    public int getHeight() { return height; }
    public boolean isAlive() { return alive; }

    public abstract void update();
    public abstract void render(Graphics2D g);
    public void setPosition(double x, double y) {
        position.setX(x);
        position.setY(y);
    }
    protected void move(double dx, double dy) {
        position.add(dx, dy);
    }
    protected void setAlive(boolean alive) {
        this.alive = alive;
    }

    public Rectangle getBounds() {
        return new Rectangle((int) position.getX(), (int) position.getY(),
                             width, height);
    }
}