package view.selection;

import view.navigation.Clickable;
import view.navigation.SceneButton;
import view.navigation.Selectable;

public class CarSelectionPanel extends SceneButton implements Clickable {

    @Override
    public void onClick(Object apiHandler, String playerID) {
        setSelectedCar
        apiHandler.setGameState();
        apiHandler.setGameMode();
    }
}
