package engine.core;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.HashMap;

public class ImageManager {
    private static ImageManager ourInstance;

    private static HashMap<String, BufferedImage> loadedImages = new HashMap<>();

    public static ImageManager getInstance() {
        if(ourInstance == null) ourInstance = new ImageManager();
        return ourInstance;
    }

    private ImageManager() {

    }

    public BufferedImage loadImage(String path) throws IOException {
        if(loadedImages.get(path) != null)
            return loadedImages.get(path);
        BufferedImage image = ImageIO.read(getClass().getResource(path));
        loadedImages.put(path, image);
        return image;
    }
}
