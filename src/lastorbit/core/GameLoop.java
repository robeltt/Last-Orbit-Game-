package lastorbit.core;


public class GameLoop implements Runnable {

    private final GamePanel panel;
    private final int targetFps;
    private boolean running;   
    private Thread thread;

    public GameLoop(GamePanel panel, int targetFps) {
        this.panel = panel;
        this.targetFps = targetFps;
        this.running = false;
    }

    public void start() {
        if (running) {
            return;
        }
        running = true;
        thread = new Thread(this, "GameLoop");
        thread.start();  // creates a thread, one thread for the window to be responsive(swing's own thread) and one thread for the game loop to run 
    }

    public void stop() {
        running = false;
    }

    @Override
    public void run() {
        final long frameTimeNanos = 1_000_000_000L / targetFps;
        while (running) {
            long frameStart = System.nanoTime();

            panel.updateGame();// update the game state
            panel.repaint();// repaint the panel then paintComponent will be called   

            long elapsed = System.nanoTime() - frameStart;
            long sleepNanos = frameTimeNanos - elapsed;
            if (sleepNanos > 0) {
                try {
                    Thread.sleep(sleepNanos / 1_000_000L,
                                 (int) (sleepNanos % 1_000_000L));//thread.sleep takes milliseconds and nanoseconds
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt(); 
                    running = false;
                }
            }
        }
    }
}