package lastorbit.core;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import lastorbit.util.InputSource;

public class InputHandler implements KeyListener, InputSource {
    private boolean upPressed;
    private boolean downPressed;

    @Override
    public void keyPressed(KeyEvent e) { setState(e.getKeyCode(), true); }

    @Override
    public void keyReleased(KeyEvent e) { setState(e.getKeyCode(), false); }

    @Override
    public void keyTyped(KeyEvent e) {}

   
    public void keyPressedCode(int keyCode)  { setState(keyCode, true); }
    public void keyReleasedCode(int keyCode) { setState(keyCode, false); }

    private void setState(int keyCode, boolean pressed) {
        if (keyCode == KeyEvent.VK_UP || keyCode == KeyEvent.VK_W) {
            upPressed = pressed;
        } else if (keyCode == KeyEvent.VK_DOWN || keyCode == KeyEvent.VK_S) {
            downPressed = pressed;
        }
    }

    @Override
    public boolean isUpPressed() { return upPressed; }

    @Override
    public boolean isDownPressed() { return downPressed; }
}