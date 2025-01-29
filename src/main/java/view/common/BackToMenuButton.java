package view.common;

import view.navigation.Clickable;
import view.navigation.SceneButton;


public class BackToMenuButton extends SceneButton implements Clickable {


    @Override
    public void onClick(Object apiHandler, String playerID) {
        apiHandler.getGameStateApi().setGameState(MAIN_MENU);
        apiHandler.getGameModeApi().setGameMode(NOT_SET);
    }
}
