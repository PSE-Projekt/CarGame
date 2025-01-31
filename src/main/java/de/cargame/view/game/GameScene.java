package de.cargame.view.game;

import de.cargame.controller.entity.GameMode;
import de.cargame.view.ApiHandler;
import de.cargame.view.CustomScene;
import javafx.scene.layout.VBox;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

public class GameScene extends CustomScene {
    private List<GameInstanceView> gameInstanceViews;

    public GameScene(ApiHandler apiHandler) {
        super(apiHandler);
    }

    public void setup(String playerOneID, Optional<String> playerTwoID) throws IllegalArgumentException {
        if (this.apiHandler.getGameStateApi().getGameMode().equals(GameMode.SINGLEPLAYER)) {
            gameInstanceViews.add(new GameInstanceView(apiHandler, playerOneID));
        }

        if (this.apiHandler.getGameStateApi().getGameMode().equals(GameMode.MULTIPLAYER)) {
            if (playerTwoID.isEmpty()) {
                throw new IllegalArgumentException("Player two ID is missing");
            }
            gameInstanceViews.add(new GameInstanceView(apiHandler, playerTwoID.get()));
        }

        ((VBox) this.getRoot()).getChildren().addAll(gameInstanceViews);
    }

    @Override
    public void render() {
        for (GameInstanceView gameInstanceView : gameInstanceViews) {
            gameInstanceView.render();
        }
    }
}
