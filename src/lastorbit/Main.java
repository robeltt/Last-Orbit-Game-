package lastorbit;

import java.awt.image.BufferedImage;

import lastorbit.core.Game;
import lastorbit.exceptions.AssetLoadException;
import lastorbit.util.ResourceLoader;

public class Main {

    public static void main(String[] args) {
        try {
            BufferedImage background = ResourceLoader.loadImage("resources/background.png");
            Game game = new Game(background);
            game.start();
        } catch (AssetLoadException e) {
            System.out.println("Failed to load game assets: " + e.getMessage());
        }
    }
}