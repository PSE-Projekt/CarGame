package view.menu;

import mockController.entity.GameMode;
import mockController.entity.GameState;
import view.ApiHandler;
import view.navigation.Clickable;
import view.navigation.SceneButton;

public class SinglePlayerButton extends SceneButton implements Clickable {
    public SinglePlayerButton() {
        super();
    }

    @Override
    public void onClick(ApiHandler apiHandler, String playerID) {
        apiHandler.getGameStateApi().setGameState(GameState.CAR_SELECTION);
        apiHandler.getGameStateApi().setGameMode(GameMode.SINGLEPLAYER);
    }
}
