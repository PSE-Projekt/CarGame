package de.cargame.view.menu;

import de.cargame.controller.entity.GameMode;
import de.cargame.controller.entity.GameState;
import de.cargame.view.ApiHandler;
import de.cargame.view.navigation.Clickable;
import de.cargame.view.navigation.SceneButton;

/**
 * Provides functionality for starting a multiplayer game by
 * implementing the onClick() method of Clickable.
 */
class MultiPlayerButton extends SceneButton implements Clickable {
    /**
     * Creates a new Multiplayer button for the MenuScene
     */
    MultiPlayerButton() {
        super("/frontend/multiP_default.png", "/frontend/multiP_selected.png");
    }

    /**
     * Sets game to multiplayer mode, assigning the user who clicked the button as the
     * player using the playerID. Necessary data will be refreshed using
     * functions from the APIHandler
     */
    @Override
    public void onClick(ApiHandler apiHandler, String playerID) {
        apiHandler.getGameStateApi().setGameState(GameState.CAR_SELECTION);
        apiHandler.getGameStateApi().setGameMode(GameMode.MULTIPLAYER);
        apiHandler.switchScene(GameState.CAR_SELECTION);
    }
}
