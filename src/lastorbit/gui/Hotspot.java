package lastorbit.gui;

public class Hotspot {

    private final int x;
    private final int y;
    private final int w;
    private final int h;
    private final String tag;

    public Hotspot(int x, int y, int w, int h, String tag) {
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
        this.tag = tag;
    }

    public boolean contains(int px, int py) {
        return px >= x && px <= x + w && py >= y && py <= y + h;
    }

    public String getTag() {
        return tag;
    }
}