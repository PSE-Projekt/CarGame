package de.cargame.view.menu;

import de.cargame.controller.entity.GameMode;
import de.cargame.controller.entity.GameState;
import de.cargame.view.ApiHandler;
import de.cargame.view.navigation.Clickable;
import de.cargame.view.navigation.SceneButton;

class SinglePlayerButton extends SceneButton implements Clickable {
    SinglePlayerButton() {
        super("/frontend/singleP_default.png", "/frontend/singleP_selected.png");
    }

    @Override
    public void onClick(ApiHandler apiHandler, String playerID) {
        apiHandler.getGameStateApi().setGameState(GameState.CAR_SELECTION);
        apiHandler.getGameStateApi().setGameMode(GameMode.SINGLEPLAYER);
        apiHandler.setPlayerOneId(playerID);
        apiHandler.switchScene(GameState.CAR_SELECTION);
    }
}
