package lastorbit.core;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JPanel;

import lastorbit.gui.ScreenManager;

public class GamePanel extends JPanel implements KeyListener {
 
    @SuppressWarnings("unused")
    private final int width;
    @SuppressWarnings("unused")
    private final int height;
    private final ScreenManager screens;

    public GamePanel(int width, int height, ScreenManager screens) {
        this.width = width;
        this.height = height;
        this.screens = screens;
        setPreferredSize(new Dimension(width, height));
        setBackground(Color.BLACK);
        setDoubleBuffered(true);
        setFocusable(true);
        addKeyListener(this);// add key listener to the panel(this)
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                screens.handleClick(e.getX(), e.getY());
            }
        });
    }

    public void updateGame() {
        screens.update();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        screens.render((Graphics2D) g);
    }

    @Override
    public void keyPressed(KeyEvent e)  { screens.handleKeyPressed(e.getKeyCode()); }

    @Override
    public void keyReleased(KeyEvent e) { screens.handleKeyReleased(e.getKeyCode()); }

    @Override
    public void keyTyped(KeyEvent e)    { }
}