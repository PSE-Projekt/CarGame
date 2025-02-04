package de.cargame.view;


import de.cargame.config.GameConfig;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

import static de.cargame.config.GameConfig.SCREEN_HEIGHT;
import static de.cargame.config.GameConfig.SCREEN_WIDTH;

public abstract class CustomScene extends Scene {
    protected final ApiHandler apiHandler;

    public CustomScene(ApiHandler apiHandler) {
        super(new VBox(), SCREEN_WIDTH, SCREEN_HEIGHT);
        this.apiHandler = apiHandler;
    }

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

    public abstract void setup();
}