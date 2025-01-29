package view.selection;

import view.ApiHandler;
import view.navigation.Clickable;
import view.navigation.SceneButton;
import view.navigation.Selectable;

public class CarSelectionPanel extends SceneButton implements Clickable {

    @Override
    public void onClick(ApiHandler apiHandler, String playerID) {
        setSelectedCar
        apiHandler.getGameStateApi().setGameState();
        apiHandler.getGameStateApi().setGameMode();
    }
}
