package lastorbit.util;

import java.awt.image.BufferedImage;

public class Animation {
    private final BufferedImage[] frames;
    private final long frameDurationNanos;
    private int currentIndex;
    private long lastSwitchTime;

    public Animation(BufferedImage[] frames, int fps) {
        this.frames = frames;
        this.frameDurationNanos = 1_000_000_000L / fps;
        this.currentIndex = 0;
        this.lastSwitchTime = System.nanoTime();
    }

    public void update() {
        long now = System.nanoTime();
        if (now - lastSwitchTime >= frameDurationNanos) {
            currentIndex = (currentIndex + 1) % frames.length;  
            lastSwitchTime = now;
        }
    }

    public BufferedImage getCurrentFrame() {
        return frames[currentIndex];
    }
}
