package de.cargame.view.game;

import de.cargame.config.GameConfig;
import de.cargame.controller.api.PlayerAPI;
import de.cargame.controller.entity.GameMode;
import de.cargame.view.ApiHandler;
import de.cargame.view.CustomScene;
import javafx.animation.AnimationTimer;
import javafx.geometry.Pos;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.List;

public class GameScene extends CustomScene {
    private final List<GameInstanceView> gameInstanceViews;

    public GameScene(ApiHandler apiHandler) {
        super(apiHandler);
        this.gameInstanceViews = new ArrayList<>();
    }

    private void configureSceneRoot() {
        VBox configurableRoot = (VBox) this.getRoot();

        configurableRoot.setMaxHeight(GameConfig.SCREEN_HEIGHT);
        configurableRoot.setMinHeight(GameConfig.SCREEN_HEIGHT);
        configurableRoot.setPrefHeight(GameConfig.SCREEN_WIDTH);
        configurableRoot.setMaxWidth(GameConfig.SCREEN_WIDTH);
        configurableRoot.setMinWidth(GameConfig.SCREEN_WIDTH);
        configurableRoot.setPrefWidth(GameConfig.SCREEN_WIDTH);
        configurableRoot.setAlignment(Pos.CENTER);

        configurableRoot.getChildren().addAll(gameInstanceViews);
    }

    public void render() {
        for (GameInstanceView gameInstanceView : gameInstanceViews) {
            gameInstanceView.render();
        }
    }

    @Override
    public void setup() throws IllegalStateException {
        GameMode currentGameMode = this.apiHandler.getGameStateApi().getGameMode();
        PlayerAPI playerApi = this.apiHandler.getPlayerApi();

        if (currentGameMode.equals(GameMode.MULTIPLAYER)) {

            if (playerApi.getKeyboardPlayer() == null || playerApi.getGamepadPlayer() == null) {
                throw new IllegalStateException("One of the players does not exist");
            }

            this.gameInstanceViews.add(new GameInstanceView(apiHandler, playerApi.getKeyboardPlayerId()));
            this.gameInstanceViews.add(new GameInstanceView(apiHandler, playerApi.getGamepadPlayerId()));

        } else if (currentGameMode.equals(GameMode.SINGLEPLAYER)) {
            gameInstanceViews.add(new GameInstanceView(apiHandler, this.apiHandler.getPlayerOneId()));
        } else {
            throw new IllegalStateException("Game mode not specified yet");
        }

        this.configureSceneRoot();
    }
}
