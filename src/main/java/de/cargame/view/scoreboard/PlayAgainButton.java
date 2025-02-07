package de.cargame.view.scoreboard;

import de.cargame.controller.entity.GameState;
import de.cargame.view.ApiHandler;
import de.cargame.view.navigation.Clickable;
import de.cargame.view.navigation.SceneButton;

/**
 * Provides functionality for returning to the selection screen
 * by implementing the onClick() method from Clickable.
 */
public class PlayAgainButton extends SceneButton implements Clickable {
    /**
     * Creates a new PlayAgainButton object. Used in the ScoreBoardScene.
     */
    public PlayAgainButton() {
        super("/frontend/replay_default.png", "/frontend/replay_selected.png");
    }

    /**
     * Returns to the corresponding CarSelectionScreen. Necessary data will be refreshed using
     * functions from the APIHandler
     */
    @Override
    public void onClick(ApiHandler apiHandler, String playerID) {
        apiHandler.getGameStateApi().setGameState(GameState.CAR_SELECTION);
        apiHandler.getGameInstanceApi().resetGameInstances();
        apiHandler.switchScene(GameState.CAR_SELECTION);
    }
}
