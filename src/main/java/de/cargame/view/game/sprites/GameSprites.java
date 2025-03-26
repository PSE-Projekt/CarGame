package de.cargame.view.game.sprites;

import de.cargame.view.game.GameObjectView;
import javafx.scene.image.Image;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Base class for managing sprites of all GameObjects.
 * Also defines paths of mentioned sprites and loads them upon initialization.
 */
@Slf4j
abstract class GameSprites {
    protected final List<String> paths;
    protected final List<Image> images = new ArrayList<>();
    protected int weight;

    /**
     * Initializes the GameSprites object by setting the paths and weights of the sprites.
     */
    GameSprites() {
        this.paths = new ArrayList<>();

        setPaths();
        setWeight();
        paths.forEach(path -> images.add(loadSprite(path)));
    }

    /**
     * Sets the weight of the sprite for the z-index.
     */
    protected abstract void setWeight();

    private Image loadSprite(String path) {
        Image image;

        try {
            image = new Image(Objects.requireNonNull(getClass().getResource(path)).toExternalForm());
        } catch (NullPointerException | IllegalArgumentException e) {
            log.error("image in path: " + path + " couldn't be loaded");
            image = new Image(Objects.requireNonNull(
                    getClass().getResource("/sprites/missing.png")).toExternalForm()
            );
        }

        return image;
    }

    /**
     * Sets the paths of the sprites.
     */
    protected abstract void setPaths();

    /**
     * Chooses a random Sprite of the fitting category, using the ID
     *
     * @param gameObjectId ID of the sprite category
     */
    public GameObjectView getRandomSprite(String gameObjectId) {
        return new GameObjectView(images.get(Math.abs(gameObjectId.hashCode()) % images.size()), weight, gameObjectId);
    }
}
