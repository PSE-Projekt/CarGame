package de.cargame.view.scoreboard;

import de.cargame.model.entity.player.Player;
import de.cargame.view.ApiHandler;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class ScoreViewSinglePlayer extends VBox {
    public ScoreViewSinglePlayer(ApiHandler apiHandler) throws IllegalStateException {
        this.setSpacing(10);

        Player playerOne = apiHandler.getPlayerOne();

        if (playerOne == null) {
            throw new IllegalStateException("Player one is not declared");
        }

        Text scoreText = new Text("Score: " + playerOne.getScore());

        this.getChildren().add(scoreText);
    }
}
