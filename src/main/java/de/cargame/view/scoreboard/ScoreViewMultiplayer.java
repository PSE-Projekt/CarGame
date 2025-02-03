package de.cargame.view.scoreboard;

import de.cargame.model.entity.player.Player;
import de.cargame.view.ApiHandler;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class ScoreViewMultiplayer extends VBox {
    public ScoreViewMultiplayer(ApiHandler apiHandler) throws IllegalStateException {
        this.setSpacing(10);

        Player playerKeyboard = apiHandler.getPlayerApi().getKeyboardPlayer();
        Player playerGamePad = apiHandler.getPlayerApi().getGamepadPlayer();

        if (playerKeyboard == null || playerGamePad == null) {
            throw new IllegalStateException("Player one or two are not declared");
        }

        Text scoreTextPlayerOne = new Text("Score: " + playerKeyboard.getScore());
        Text scoreTextPlayerTwo = new Text("Score: " + playerGamePad.getScore());

        this.getChildren().addAll(scoreTextPlayerOne, scoreTextPlayerTwo);
    }
}
