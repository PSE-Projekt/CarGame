package de.cargame.view.scoreboard;

import de.cargame.controller.entity.GameState;
import de.cargame.view.ApiHandler;
import de.cargame.view.navigation.Clickable;
import de.cargame.view.navigation.SceneButton;

public class PlayAgainButton extends SceneButton implements Clickable {

    public PlayAgainButton() {
        super("/frontend/replay_default.png", "/frontend/replay_selected.png");
    }

    @Override
    public void onClick(ApiHandler apiHandler, String playerID) {
        apiHandler.getGameStateApi().setGameState(GameState.CAR_SELECTION);
    }
}
