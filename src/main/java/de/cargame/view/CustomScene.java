package de.cargame.view;


import de.cargame.config.ConfigKey;
import de.cargame.config.GameConfigService;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

/**
 * An abstract representation of a scene serving as the schematic for specific scenes,
 * ensuring modularity in scene management.
 * This class defines the structure and behavior for all application scenes,
 * The provided APIs in its parameters assist in managing game state transitions, as well as other
 * key functionalities.
 */
public abstract class CustomScene extends Scene {
    protected final ApiHandler apiHandler;

    protected final int SCREEN_WIDTH;
    protected final int SCREEN_HEIGHT;

    /**
     * Creates a new CustomScene, which will be displayed throughout the application's course.
     *
     * @param apiHandler An instance of {@code ApiHandler} that provides functionality
     *                   for managing game state transitions as well as other key operations.
     */
    protected CustomScene(ApiHandler apiHandler) {
        super(new VBox(),
                GameConfigService.getInstance().loadInteger(ConfigKey.SCREEN_WIDTH),
                GameConfigService.getInstance().loadInteger(ConfigKey.SCREEN_HEIGHT)
        );
        this.SCREEN_WIDTH = GameConfigService.getInstance().loadInteger(ConfigKey.SCREEN_WIDTH);
        this.SCREEN_HEIGHT = GameConfigService.getInstance().loadInteger(ConfigKey.SCREEN_HEIGHT);

        this.apiHandler = apiHandler;
    }

    /**
     * prepares the scene's root, using the defined window size and a black background as
     * the top and bottom borders
     */
    protected void configureRoot() {
        VBox root = (VBox) this.getRoot();

        root.setAlignment(Pos.CENTER);
        root.setBackground(new Background(new BackgroundFill(Color.BLACK, null, null)));
        root.setMaxHeight(SCREEN_HEIGHT);
        root.setMinHeight(SCREEN_HEIGHT);
        root.setPrefHeight(SCREEN_WIDTH);
        root.setMaxWidth(SCREEN_WIDTH);
        root.setMinWidth(SCREEN_WIDTH);
        root.setPrefWidth(SCREEN_WIDTH);
        root.setAlignment(Pos.CENTER);
    }

    /**
     * refreshes the Scene and it's contents to it's original, untouched state.
     */
    protected abstract void setup();
}