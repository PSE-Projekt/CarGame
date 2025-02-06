package de.cargame.view.menu;

import de.cargame.view.ApiHandler;
import de.cargame.view.navigation.Navigator;
/**
 * Implements the methods provided by Navigator. Purpose is
 * interaction and navigation the button options in the menu scene.
 */
public class MenuNavigator extends Navigator {
    /**
     * Creates a new MenuNavigator instance, and passes a ApiHandler to it.
     */
    public MenuNavigator(ApiHandler apiHandler) {
        super(apiHandler);
    }
}
