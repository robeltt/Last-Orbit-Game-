package lastorbit.gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import lastorbit.exceptions.AssetLoadException;
import lastorbit.rules.RuleEngine;

public class MainMenuScreen extends Screen {

    private final int width;
    private final int height;
    private final BufferedImage background;
    private final ScreenFactory factory;

    private final Button quickPlay;
    private final Button configure;
    private final Button quit;

    public MainMenuScreen(int width, int height, BufferedImage background,
                          ScreenFactory factory) {
        this.width = width;
        this.height = height;
        this.background = background;
        this.factory = factory;
        int bw = 280, bh = 56, cx = width / 2 - bw / 2;
        this.quickPlay = new Button(cx, 320, bw, bh, "QUICK PLAY");
        this.configure = new Button(cx, 392, bw, bh, "CONFIGURE RULES");
        this.quit = new Button(cx, 464, bw, bh, "QUIT");
    }

    @Override
    public void update() {
    }

    @Override
    public void render(Graphics2D g) {
        g.drawImage(background, 0, 0, width, height, null);
        g.setColor(new Color(0, 0, 0, 120));
        g.fillRect(0, 0, width, height);

        g.setFont(new Font("Consolas", Font.BOLD, 72));
        g.setColor(new Color(120, 200, 255));
        String title = "LAST ORBIT";
        int tw = g.getFontMetrics().stringWidth(title);
        g.drawString(title, width / 2 - tw / 2, 200);

        g.setFont(new Font("Consolas", Font.PLAIN, 20));
        g.setColor(Color.LIGHT_GRAY);
        String sub = "Defend Earth. One breach and it's over.";
        int sw = g.getFontMetrics().stringWidth(sub);
        g.drawString(sub, width / 2 - sw / 2, 245);

        quickPlay.render(g, false);
        configure.render(g, false);
        quit.render(g, false);
    }

    @Override
    public void handleClick(int x, int y) {
        if (quickPlay.contains(x, y)) {
            startGame(new RuleEngine());              
        } else if (configure.contains(x, y)) {
            getManager().setScreen(
                new RuleConfigScreen(width, height, background, factory));                 
        } else if (quit.contains(x, y)) {
            System.exit(0);
        }
    }

    private void startGame(RuleEngine engine) {
        try {
            getManager().setScreen(factory.createGame(engine));
        } catch (AssetLoadException e) {
        }
    }
}