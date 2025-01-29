package view.common;

import mockController.entity.GameMode;
import mockController.entity.GameState;
import view.ApiHandler;
import view.navigation.Clickable;
import view.navigation.SceneButton;


public class BackToMenuButton extends SceneButton implements Clickable {


    @Override
    public void onClick(ApiHandler apiHandler, String playerID) {
        apiHandler.getGameStateApi().setGameState(GameState.MAIN_MENU);
        apiHandler.getGameStateApi().setGameMode(GameMode.NOT_SET);
    }
}
