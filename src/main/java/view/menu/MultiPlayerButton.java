package view.menu;

import mockController.entity.GameMode;
import mockController.entity.GameState;
import view.ApiHandler;
import view.navigation.Clickable;
import view.navigation.SceneButton;
import view.navigation.Selectable;

public class MultiPlayerButton extends SceneButton implements Clickable {
    public MultiPlayerButton() {
        super();
    }

    @Override
    public void onClick(ApiHandler apiHandler, String playerID) {
        apiHandler.getGameStateApi().setGameState(GameState.CAR_SELECTION);
        apiHandler.getGameStateApi().setGameMode(GameMode.MULTIPLAYER);
    }
}
