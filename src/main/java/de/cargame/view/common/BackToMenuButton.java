package de.cargame.view.common;

import de.cargame.controller.entity.GameMode;
import de.cargame.controller.entity.GameState;
import de.cargame.view.ApiHandler;
import de.cargame.view.navigation.Clickable;
import de.cargame.view.navigation.SceneButton;


public class BackToMenuButton extends SceneButton implements Clickable {


    public BackToMenuButton() {
        super("/frontend/backToMenu_default.png", "/frontend/backToMenu_selected.png");
    }

    @Override
    public void onClick(ApiHandler apiHandler, String playerID) {
        apiHandler.getGameStateApi().setGameState(GameState.MAIN_MENU);
        apiHandler.getGameStateApi().setGameMode(GameMode.NOT_SET);
        apiHandler.switchScene(GameState.MAIN_MENU);
    }
}
