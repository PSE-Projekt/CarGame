package de.cargame.view.game;

import de.cargame.config.GameConfig;
import de.cargame.controller.entity.GameMode;
import de.cargame.view.ApiHandler;
import de.cargame.view.CustomScene;
import javafx.geometry.Pos;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

public class GameScene extends CustomScene {
    private final List<GameInstanceView> gameInstanceViews;

    public GameScene(ApiHandler apiHandler) {
        super(apiHandler);
        this.gameInstanceViews = new ArrayList<>();
    }

    public void setup(String playerOneId) {
        gameInstanceViews.add(new GameInstanceView(apiHandler, playerOneId));
    }

    public void setup(String playerOneId, String playerTwoId) {
        gameInstanceViews.add(new GameInstanceView(apiHandler, playerOneId));
        this.gameInstanceViews.add(new GameInstanceView(apiHandler, playerTwoId));
        this.configureSceneRoot();
    }

    private void configureSceneRoot() {
        VBox configurableRoot = ((VBox) this.getRoot());

        configurableRoot.setMaxHeight(GameConfig.SCREEN_HEIGHT);
        configurableRoot.setMinHeight(GameConfig.SCREEN_HEIGHT);
        configurableRoot.setPrefHeight(GameConfig.SCREEN_WIDTH);
        configurableRoot.setMaxWidth(GameConfig.SCREEN_WIDTH);
        configurableRoot.setMinWidth(GameConfig.SCREEN_WIDTH);
        configurableRoot.setPrefWidth(GameConfig.SCREEN_WIDTH);
        configurableRoot.setAlignment(Pos.CENTER);

        configurableRoot.getChildren().addAll(gameInstanceViews);
    }

    @Override
    public void render() {
        for (GameInstanceView gameInstanceView : gameInstanceViews) {
            gameInstanceView.render();
        }
    }

    @Override
    public void setup() {

    }
}
