package de.cargame.view.menu;

import de.cargame.controller.input.UserInputBundle;
import de.cargame.view.ApiHandler;
import de.cargame.view.navigation.Navigator;

public class MenuNavigator extends Navigator {
    public MenuNavigator(ApiHandler apiHandler) {
        super(apiHandler);
    }

    @Override
    public void receiveInput(UserInputBundle userInputBundle, String playerID) {

    }
}
