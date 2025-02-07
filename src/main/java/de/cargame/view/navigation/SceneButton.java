package de.cargame.view.navigation;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;
/**
 * Serves as the foundation for the buttons in navigable scenes. Child classes
 * will implement the functions provided by Selectable.
 */
public abstract class SceneButton extends Selectable implements Clickable {
    private final Image defaultDisplay;
    private final Image displayOnSelection;
    private final ImageView display;
    /**
     * Creates a new SceneButton which will be displayed in a fitting scene.
     * @param pathDefaultImg image for the idle button
     * @param pathSelectedImg image for the button upon interaction
     */
    public SceneButton(String pathDefaultImg, String pathSelectedImg) {
        this.defaultDisplay = loadImage(pathDefaultImg);
        this.displayOnSelection = loadImage(pathSelectedImg);
        this.display = new ImageView(defaultDisplay);

        this.getChildren().add(display);
    }

    private Image loadImage(String path) {
        Image image = null;
        try {
            image = new Image(Objects.requireNonNull(getClass().getResourceAsStream(path)));
        } catch (NullPointerException | IllegalArgumentException e) {
            System.err.println("image in path: " + path + " couldn't be loaded");
        }
        return image;
    }

    private static Image loadAndDisplayImage(String path) {
        try (InputStream input = SceneButton.class.getClassLoader().getResourceAsStream(path)) {
            if (input == null) {
                throw new IOException("Image not found at path: " + path);
            }
            BufferedImage image = ImageIO.read(input);
            if (image == null) {
                throw new IOException("Failed to decode image at path: " + path);
            }
            System.out.println("Successfully loaded: " + path);
            WritableImage wr = new WritableImage(image.getWidth(), image.getHeight());
            PixelWriter pw = wr.getPixelWriter();
            for (int x = 0; x < image.getWidth(); x++) {
                for (int y = 0; y < image.getHeight(); y++) {
                    pw.setArgb(x, y, image.getRGB(x, y));
                }
            }

            return new ImageView(wr).getImage();
        } catch (IOException e) {
            System.err.println("Error loading image: " + e.getMessage());
        }
        throw new RuntimeException("Could not load image at path: " + path);
    }

    @Override
    public void select() {
        display.setImage(displayOnSelection);
    }

    @Override
    public void deselect() {
        display.setImage(defaultDisplay);
    }
}
