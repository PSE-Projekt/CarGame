package de.cargame.view.menu;

import de.cargame.controller.entity.GameMode;
import de.cargame.controller.entity.GameState;
import de.cargame.view.ApiHandler;
import de.cargame.view.navigation.Clickable;
import de.cargame.view.navigation.SceneButton;

/**
 * Provides functionality for starting a singleplayer game by
 * implementing the onClick() method of Clickable.
 */
class SinglePlayerButton extends SceneButton implements Clickable {
    /**
     * Creates a new Singleplayer button for the MenuScene
     */
    SinglePlayerButton() {
        super("/frontend/singleP_default.png", "/frontend/singleP_selected.png");
    }

    /**
     * Sets game to singleplayer mode, assigning the user who clicked the button as the
     * player using the playerID. Necessary data will be refreshed using
     * functions from the APIHandler
     */
    @Override
    public void onClick(ApiHandler apiHandler, String playerID) {
        apiHandler.getGameStateApi().setGameState(GameState.CAR_SELECTION);
        apiHandler.getGameStateApi().setGameMode(GameMode.SINGLEPLAYER);
        apiHandler.setPlayerOneId(playerID);
        apiHandler.switchScene(GameState.CAR_SELECTION);
    }
}
