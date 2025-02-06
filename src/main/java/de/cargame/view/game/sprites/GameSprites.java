package de.cargame.view.game.sprites;

import de.cargame.view.game.GameObjectView;
import javafx.scene.image.Image;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public abstract class GameSprites {
    protected final List<String> paths;
    protected final List<Image> images = new ArrayList<>();
    protected int weight;

    public GameSprites() {
        this.paths = new ArrayList<>();

        setPaths();
        setWeight();
        paths.forEach(path -> {
            try {
                images.add(loadSprite(path));
            } catch (RuntimeException e) {
                System.err.println(e.getMessage());
            }
        });
    }

    protected abstract void setWeight();

    private Image loadSprite(String path) {
        Image image;

        try {
            image = new Image(Objects.requireNonNull(getClass().getResource(path)).toExternalForm());
        } catch (NullPointerException | IllegalArgumentException e) {
            System.err.println("Could not load sprite from path: " + path);
            image = new Image(Objects.requireNonNull(
                    getClass().getResource("/sprites/missing.png")).toExternalForm()
            );
        }

        return image;
    }

    protected abstract void setPaths();

    public GameObjectView getRandomSprite(String gameObjectId) {
        return new GameObjectView(images.get(Math.abs(gameObjectId.hashCode()) % images.size()), weight, gameObjectId);
    }
}
