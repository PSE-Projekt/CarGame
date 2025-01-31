package de.cargame.view;


import javafx.scene.Scene;
import javafx.scene.layout.VBox;

import static de.cargame.config.GameConfig.SCREEN_HEIGHT;
import static de.cargame.config.GameConfig.SCREEN_WIDTH;

public abstract class CustomScene extends Scene {
    protected final ApiHandler apiHandler;


    public CustomScene(ApiHandler apiHandler) {
        super(new VBox(), SCREEN_WIDTH, SCREEN_HEIGHT);
        this.apiHandler = apiHandler;
    }

    public abstract void render();
}