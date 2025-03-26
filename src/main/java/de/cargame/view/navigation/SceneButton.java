package de.cargame.view.navigation;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import lombok.extern.slf4j.Slf4j;

import java.util.Objects;

/**
 * Serves as the foundation for the buttons in navigable scenes. Child classes
 * will implement the functions provided by Selectable.
 */
@Slf4j
public abstract class SceneButton extends Selectable implements Clickable {
    protected final Image defaultDisplay;
    protected final Image displayOnSelection;
    protected final ImageView display;

    /**
     * Creates a new SceneButton which will be displayed in a fitting scene.
     *
     * @param pathDefaultImg  image for the idle button
     * @param pathSelectedImg image for the button upon interaction
     */
    protected SceneButton(String pathDefaultImg, String pathSelectedImg) {
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
            log.error("image in path: " + path + " couldn't be loaded");
        }
        return image;
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
