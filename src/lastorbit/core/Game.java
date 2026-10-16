package lastorbit.core;

import java.awt.image.BufferedImage;
import javax.swing.JFrame;

import lastorbit.audio.SoundManager;
import lastorbit.gui.ScreenFactory;
import lastorbit.gui.ScreenManager;

public class Game {

    private static final int WIDTH = 1280;
    private static final int HEIGHT = 720;
    private static final int TARGET_FPS = 60;

    private final JFrame frame;
    private final GamePanel panel;
    private final GameLoop loop;

    public Game(BufferedImage background) {
        ScreenManager screens = new ScreenManager();
        ScreenFactory factory = new ScreenFactory(WIDTH, HEIGHT, background);
        screens.setScreen(factory.createMenu()); 

        SoundManager sound = new SoundManager();
        sound.playMusicLoop("resources/sounds/music.wav");
        sound.setVolume(0.7f);

        panel = new GamePanel(WIDTH, HEIGHT, screens);
        loop = new GameLoop(panel, TARGET_FPS);

        frame = new JFrame("Last Orbit");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false); 
        frame.add(panel);// add the panel to the frame
        frame.pack();// to make the frame fit the panel
        frame.setLocationRelativeTo(null);// to make it center
    }

    public void start() {
        frame.setVisible(true);
        panel.requestFocusInWindow();
        loop.start();
    }
}