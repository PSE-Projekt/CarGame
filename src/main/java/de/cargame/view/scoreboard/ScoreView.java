package de.cargame.view.scoreboard;

import de.cargame.controller.api.PlayerAPI;
import de.cargame.controller.entity.GameMode;
import de.cargame.model.entity.player.Player;
import de.cargame.view.ApiHandler;
import javafx.geometry.Pos;
import javafx.scene.layout.VBox;

import java.util.PriorityQueue;
import java.util.Queue;

public class ScoreView extends VBox {
    public ScoreView(ApiHandler apiHandler) throws IllegalArgumentException {
        configureSelf();

        VBox scoreBox = new VBox();
        scoreBox.setAlignment(Pos.CENTER);
        scoreBox.setSpacing(10);

        GameMode gameMode = apiHandler.getGameStateApi().getGameMode();

        if (gameMode == GameMode.SINGLEPLAYER) {
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

            int scoreValue = (int) player.getScore().getValue();

            ScoreDisplay scoreDisplay = new ScoreDisplay(scoreValue, 0);
            scoreBox.getChildren().add(scoreDisplay);

            this.getChildren().add(scoreBox);
        } else if (gameMode == GameMode.MULTIPLAYER) {
            Player playerKeyboard = apiHandler.getPlayerApi().getKeyboardPlayer();
            Player playerGamePad = apiHandler.getPlayerApi().getGamepadPlayer();

            if (playerKeyboard == null || playerGamePad == null) {
                throw new IllegalStateException("Player one or two are not declared");
            }

            int scoreValuePlayerOne = (int) playerKeyboard.getScore().getValue();
            int scoreValuePlayerTwo = (int) playerGamePad.getScore().getValue();

            ScoreDisplay scoreDisplayPlayerOne = new ScoreDisplay(scoreValuePlayerOne, 1);
            ScoreDisplay scoreDisplayPlayerTwo = new ScoreDisplay(scoreValuePlayerTwo, 2);

            Queue<ScoreDisplay> scoreDisplays = new PriorityQueue<>(ScoreDisplay::compareTo);

            scoreDisplays.add(scoreDisplayPlayerOne);
            scoreDisplays.add(scoreDisplayPlayerTwo);

            while (!scoreDisplays.isEmpty()) {
                scoreBox.getChildren().add(scoreDisplays.poll());
            }

            this.getChildren().add(scoreBox);
        } else {
            throw new IllegalArgumentException("Invalid game mode");
        }
    }

    private void configureSelf() {
        this.setAlignment(Pos.CENTER);
        this.setSpacing(20);
        setPrefWidth(500);
    }
}
