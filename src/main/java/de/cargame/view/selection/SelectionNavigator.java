package de.cargame.view.selection;

import de.cargame.controller.input.UserInputBundle;
import de.cargame.view.ApiHandler;
import de.cargame.view.navigation.Navigator;

public class SelectionNavigator extends Navigator {

    public SelectionNavigator(ApiHandler apiHandler) {
        super(apiHandler);
    }

    @Override
    public void receiveInput(UserInputBundle userInputBundle, String playerID) {

    }
}
