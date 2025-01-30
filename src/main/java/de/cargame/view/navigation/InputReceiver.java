package de.cargame.view.navigation;

import de.cargame.controller.input.UserInputBundle;

public class InputReceiver {
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
