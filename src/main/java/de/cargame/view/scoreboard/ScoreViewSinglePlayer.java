package de.cargame.view.scoreboard;

import de.cargame.controller.api.PlayerAPI;
import de.cargame.model.entity.player.Player;
import de.cargame.view.ApiHandler;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class ScoreViewSinglePlayer extends VBox {
    public ScoreViewSinglePlayer(ApiHandler apiHandler) throws IllegalStateException {
        this.setSpacing(10);

        String playerId = apiHandler.getPlayerOneId();
        PlayerAPI playerApi = apiHandler.getPlayerApi();
        Player player;

        if (playerApi.getKeyboardPlayerId().equals(playerId)) {
            player = playerApi.getKeyboardPlayer();
        } else if (playerApi.getGamepadPlayerId().equals(playerId)) {
            player = playerApi.getGamepadPlayer();
        } else {
            throw new IllegalStateException("none of the player ids is matching");
        }

        if (player == null) {
            throw new IllegalStateException("Player does not exist");
        }

        Text scoreText = new Text("Score: " + player.getScore());

        this.getChildren().add(scoreText);
    }
}
