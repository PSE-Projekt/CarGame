package de.cargame.view.common;

import de.cargame.mockController.entity.GameMode;
import de.cargame.mockController.entity.GameState;
import de.cargame.view.ApiHandler;
import de.cargame.view.navigation.Clickable;
import de.cargame.view.navigation.SceneButton;


public class BackToMenuButton extends SceneButton implements Clickable {


    @Override
    public void onClick(ApiHandler apiHandler, String playerID) {
        apiHandler.getGameStateApi().setGameState(GameState.MAIN_MENU);
        apiHandler.getGameStateApi().setGameMode(GameMode.NOT_SET);
    }
}
