package view.menu;

import view.navigation.Clickable;
import view.navigation.SceneButton;
import view.navigation.Selectable;

public class SinglePlayerButton extends SceneButton implements Clickable {
    public SinglePlayerButton() {
        super();
    }

    @Override
    public void onClick(Object apiHandler, String playerID) {
        apiHandler.setGameState(CAR_SELECTION);
        apiHandler.setGameMode(SINGLEPLAYER);
    }
}
