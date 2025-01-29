package view.common;

import view.navigation.Clickable;
import view.navigation.SceneButton;


public class BackToMenuButton extends SceneButton implements Clickable {


    @Override
    public void onClick(Object apiHandler, String playerID) {
        apiHandler.setGameState(MAIN_MENU);
        apiHandler.setGameMode(NOT_SET);
    }
}
