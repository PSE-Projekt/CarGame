package view.menu;

import view.navigation.Clickable;
import view.navigation.SceneButton;
import view.navigation.Selectable;

public class MultiPlayerButton extends SceneButton implements Clickable {
    public MultiPlayerButton() {
        super();
    }

    @Override
    public void onClick(Object apiHandler, String playerID) {
        apiHandler.setGameState(CAR_SELECTION);
        apiHandler.setGameMode(MULTIPLAYER);
    }
}
