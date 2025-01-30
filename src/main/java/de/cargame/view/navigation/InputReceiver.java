package de.cargame.view.navigation;

import de.cargame.controller.input.UserInputBundle;
import de.cargame.model.entity.gameobject.interfaces.UserInputObserver;

public class InputReceiver implements UserInputObserver {
    private String playerID;
    private Navigator navigator;

    public InputReceiver(String playerID){
        this.playerID = playerID;
    }

    public void update(UserInputBundle userInputBundle){
        navigator.receiveInput(userInputBundle, playerID);
    }

    public void assignNavigator(Navigator navigator){

    }
}
