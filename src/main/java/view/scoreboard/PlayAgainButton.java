package view.scoreboard;

import view.navigation.Clickable;
import view.navigation.SceneButton;

public class PlayAgainButton extends SceneButton implements Clickable {

    @Override
    public void onClick(Object apiHandler, String playerID) {
        apiHandler.setGameState(CAR_SELECTION);
    }
}
