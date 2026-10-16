package lastorbit.util;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

import lastorbit.exceptions.AssetLoadException;


public final class ResourceLoader {

    private ResourceLoader() {   
    }

    public static BufferedImage loadImage(String path) throws AssetLoadException {
        File file = new File(path);
        if (!file.exists()) {
            throw new AssetLoadException(
                "Asset not found: " + file.getAbsolutePath(), null);
        }
        try {
            BufferedImage image = ImageIO.read(file);
            if (image == null) {
                throw new AssetLoadException("Not a readable image: " + path, null);
            }
            return image;
        } catch (IOException e) {
            throw new AssetLoadException("Failed to read image: " + path, e);
        }
    }
    public static BufferedImage[] loadFrames(String baseName, int count)
            throws AssetLoadException {
        BufferedImage[] frames = new BufferedImage[count];
        for (int i = 0; i < count; i++) {
            frames[i] = loadImage("resources/sprites/" + baseName + "_" + i + ".png");
        }
        return frames;
    }
}