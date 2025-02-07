package de.cargame.view.common;

import de.cargame.controller.entity.GameMode;
import de.cargame.controller.entity.GameState;
import de.cargame.view.ApiHandler;
import de.cargame.view.navigation.Clickable;
import de.cargame.view.navigation.SceneButton;

/**
 * Provides functionality for returning to the main menu by
 * implementing the onClick() method from Clickable.
 */
public class BackToMenuButton extends SceneButton implements Clickable {

    /**
     * Creates a new BackToMenuButton object. Will be used in SelectionScene and ScoreBoardScene.
     */
    public BackToMenuButton() {
        super("/frontend/backToMenu_default.png", "/frontend/backToMenu_selected.png");
    }
    /**
     * Returns to the Main Menu. Necessary data will be refreshed using
     * functions from the APIHandler
     */
    @Override
    public void onClick(ApiHandler apiHandler, String ignored) {
        apiHandler.getGameStateApi().setGameState(GameState.MAIN_MENU);
        apiHandler.getGameStateApi().setGameMode(GameMode.NOT_SET);
        apiHandler.getGameInstanceApi().resetGameInstances();
        apiHandler.switchScene(GameState.MAIN_MENU);
    }
}
