package de.cargame.view.menu;

import de.cargame.controller.entity.GameMode;
import de.cargame.controller.entity.GameState;
import de.cargame.view.ApiHandler;
import de.cargame.view.navigation.Clickable;
import de.cargame.view.navigation.SceneButton;

class MultiPlayerButton extends SceneButton implements Clickable {
    MultiPlayerButton() {
        super("/frontend/multiP_default.png", "/frontend/multiP_selected.png");
    }

    @Override
    public void onClick(ApiHandler apiHandler, String playerID) {
        apiHandler.getGameStateApi().setGameState(GameState.CAR_SELECTION);
        apiHandler.getGameStateApi().setGameMode(GameMode.MULTIPLAYER);
        apiHandler.switchScene(GameState.CAR_SELECTION);
    }
}
