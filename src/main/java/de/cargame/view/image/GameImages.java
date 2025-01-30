package de.cargame.view.image;

import lombok.Getter;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public abstract class GameImages {

    @Getter
    protected final List<BufferedImage> images = new ArrayList<>();
    protected final List<String> paths = new ArrayList<>();


    public GameImages() {
        setPaths();
        paths.forEach(path -> images.add(loadImage(path)));
    }

    protected BufferedImage loadImage(String path) {
        try {
            return ImageIO.read(new File(path));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    protected abstract void setPaths();

    public BufferedImage getRandomImage(String seed) {
        return images.get(Math.abs(seed.hashCode() % images.size()));
    }
}
