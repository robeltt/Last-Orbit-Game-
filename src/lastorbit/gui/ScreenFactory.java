package lastorbit.gui;

import java.awt.image.BufferedImage;

import lastorbit.exceptions.AssetLoadException;
import lastorbit.rules.RuleEngine;


public class ScreenFactory {

    private final int width;
    private final int height;
    private final BufferedImage background;

    public ScreenFactory(int width, int height, BufferedImage background) {
        this.width = width;
        this.height = height;
        this.background = background;
    }

    public MainMenuScreen createMenu() {
        return new MainMenuScreen(width, height, background, this);
    }

    public GameScreen createGame(RuleEngine ruleEngine) throws AssetLoadException {
        return new GameScreen(width, height, background, ruleEngine, this);
    }

    public int getWidth()  { return width; }
    public int getHeight() { return height; }
    public BufferedImage getBackground() { return background; }
}