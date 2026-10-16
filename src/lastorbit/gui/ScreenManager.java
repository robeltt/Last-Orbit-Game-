package lastorbit.gui;

import java.awt.Graphics2D;


public class ScreenManager {

    private Screen current;

    public void setScreen(Screen screen) {
        this.current = screen;
        screen.setManager(this);
    }

    public void update() {
        if (current != null) {
            current.update();
        }
    }

    public void render(Graphics2D g) {
        if (current != null) {
            current.render(g);
        }
    }

    public void handleClick(int x, int y) {
        if (current != null) {
            current.handleClick(x, y);
        }
    }

    public void handleKeyPressed(int keyCode) {
        if (current != null) {
            current.handleKeyPressed(keyCode);
        }
    }

    public void handleKeyReleased(int keyCode) {
        if (current != null) {
            current.handleKeyReleased(keyCode);
        }
    }
}