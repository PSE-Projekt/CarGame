package de.cargame.view.scoreboard;

import de.cargame.model.entity.player.Player;
import de.cargame.view.ApiHandler;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class ScoreViewMultiplayer extends VBox {
    public ScoreViewMultiplayer(ApiHandler apiHandler) throws IllegalStateException {
        this.setSpacing(10);

        Player playerOne = apiHandler.getPlayerOne();
        Player playerTwo = apiHandler.getPlayerTwo();

        if (playerOne == null || playerTwo == null) {
            throw new IllegalStateException("Player one or two are not declared");
        }

        Text scoreTextPlayerOne = new Text("Score: " + playerOne.getScore());
        Text scoreTextPlayerTwo = new Text("Score: " + playerTwo.getScore());

        this.getChildren().addAll(scoreTextPlayerOne, scoreTextPlayerTwo);
    }
}
