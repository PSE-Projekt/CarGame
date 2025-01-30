package de.cargame.view.selection;

import de.cargame.view.ApiHandler;
import de.cargame.view.navigation.Clickable;
import de.cargame.view.navigation.SceneButton;

public class CarSelectionPanel extends SceneButton implements Clickable {

    @Override
    public void onClick(ApiHandler apiHandler, String playerID) {
        setSelectedCar
        apiHandler.getGameStateApi().setGameState();
        apiHandler.getGameStateApi().setGameMode();
    }
}
