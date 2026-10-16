package lastorbit.gui;

import java.awt.Graphics2D;


public abstract class Screen {

    private ScreenManager manager;

    public void setManager(ScreenManager manager) {
        this.manager = manager;
    }

    protected ScreenManager getManager() {
        return manager;
    }

    public abstract void update();
    public abstract void render(Graphics2D g);

    public void handleClick(int x, int y) {
    }

    public void handleKeyPressed(int keyCode) {
    }

    public void handleKeyReleased(int keyCode) {
    }
}