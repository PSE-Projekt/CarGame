package de.cargame.view.game.sprites;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public abstract class GameSprites {
    protected final List<String> paths;
    protected final List<ImageView> imageViews;

    public GameSprites() {
        this.paths = new ArrayList<>();
        this.imageViews = new ArrayList<>();

        setPaths();
        paths.forEach(path -> {
            try {
                imageViews.add(loadSprite(path));
            } catch (RuntimeException e) {
                System.err.println(e.getMessage());
            }
        });
    }

    private ImageView loadSprite(String path) {
        Image image;

        try {
            image = new Image(Objects.requireNonNull(getClass().getResource(path)).toExternalForm());
        } catch (NullPointerException | IllegalArgumentException e) {
            System.err.println("Could not load sprite from path: " + path);
            image = new Image(Objects.requireNonNull(
                    getClass().getResource("/sprites/missing.png")).toExternalForm()
            );
        }

        return new ImageView(image);
    }

    protected abstract void setPaths();

    public ImageView getRandomSprite(String gameObjectId) {
        return imageViews.get(Math.abs(gameObjectId.hashCode()) % imageViews.size());
    }
}
