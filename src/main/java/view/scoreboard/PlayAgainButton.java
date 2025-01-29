package view.scoreboard;

import mockController.entity.GameMode;
import mockController.entity.GameState;
import view.ApiHandler;
import view.navigation.Clickable;
import view.navigation.SceneButton;

public class PlayAgainButton extends SceneButton implements Clickable {

    @Override
    public void onClick(ApiHandler apiHandler, String playerID) {
        apiHandler.getGameStateApi().setGameState(GameState.CAR_SELECTION);
    }
}
