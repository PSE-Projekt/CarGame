package de.cargame.view.navigation;

import de.cargame.controller.input.UserInputBundle;
import de.cargame.model.entity.gameobject.interfaces.UserInputObserver;

import java.util.HashMap;
import java.util.Map;

/**
 * Registered in the Controller.
 * Tracks incoming input of the user and notifies the assigned Navigator upon new inputs.
 * InputReceiver is uniquely associated to a player by storing his ID. This
 * assures distinction of input sources.
 */
public class InputReceiver implements UserInputObserver {
    private final Map<String, Navigator> navigatorMap;


    public InputReceiver() {
        this.navigatorMap = new HashMap<>();
    }
    /**
     * calls 'receiveInput(...)' in the navigator,
     * passing on  a UserInputBundle and the playerID on as parameters.
     */
    @Override
    public void update(UserInputBundle userInputBundle) {
        navigatorMap.forEach((playerID, navigator) -> navigator.receiveInput(userInputBundle, playerID));
    }

    public void assignNavigator(String playerID, Navigator navigator) {
        navigatorMap.put(playerID, navigator);
    }

    public void clear() {
        navigatorMap.clear();
    }
}
