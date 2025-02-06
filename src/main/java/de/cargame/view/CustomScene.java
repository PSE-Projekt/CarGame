package de.cargame.view;


import de.cargame.config.ConfigKey;
import de.cargame.config.GameConfigService;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;


public abstract class CustomScene extends Scene {
    protected final ApiHandler apiHandler;

    protected int SCREEN_WIDTH = GameConfigService.getInstance().loadInteger(ConfigKey.SCREEN_WIDTH);
    protected int SCREEN_HEIGHT = GameConfigService.getInstance().loadInteger(ConfigKey.SCREEN_HEIGHT);

    protected CustomScene(ApiHandler apiHandler) {
        super(new VBox(),
                GameConfigService.getInstance().loadInteger(ConfigKey.SCREEN_WIDTH),
                GameConfigService.getInstance().loadInteger(ConfigKey.SCREEN_HEIGHT)
        );
        this.SCREEN_WIDTH = GameConfigService.getInstance().loadInteger(ConfigKey.SCREEN_WIDTH);
        this.SCREEN_HEIGHT = GameConfigService.getInstance().loadInteger(ConfigKey.SCREEN_HEIGHT);

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