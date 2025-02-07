package de.cargame.view.navigation;

import de.cargame.controller.input.UserInputBundle;
import de.cargame.model.entity.gameobject.interfaces.UserInputObserver;
/**
 * Registered in the Controller.
 * Tracks incoming input of the user and notifies the assigned Navigator upon new inputs.
 * InputReceiver is uniquely associated to a player by storing his ID. This
 * assures distinction of input sources.
 */
public class InputReceiver implements UserInputObserver {
    private final String playerID;
    private Navigator navigator;

    /**
     * Creates an InputReceiver for a player, using his ID as a param.
     */
    public InputReceiver(String playerID) {
        this.playerID = playerID;
    }
    /**
     * calls 'receiveInput(...)' in the navigator,
     * passing on  a UserInputBundle and the playerID on as parameters.
     */
    @Override
    public void update(UserInputBundle userInputBundle) {
        if (navigator == null) {
            return;
        }

        navigator.receiveInput(userInputBundle, playerID);
    }
    /**
     * Registers a desired navigator to itself.
     * @param navigator desired navigator
     */
    public void assignNavigator(Navigator navigator) {
        this.navigator = navigator;
    }
}
