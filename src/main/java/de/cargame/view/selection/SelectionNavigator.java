package de.cargame.view.selection;

import de.cargame.view.ApiHandler;
import de.cargame.view.navigation.Navigator;
/**
 * Implements the methods provided by Navigator. Purpose is
 * interaction and navigation the button options in the selection scene.
 */
class SelectionNavigator extends Navigator {
    /**
     * Creates a new SelectionNavigator instance, and passes a ApiHandler to it.
     */
    SelectionNavigator(ApiHandler apiHandler) {
        super(apiHandler);
    }


}
