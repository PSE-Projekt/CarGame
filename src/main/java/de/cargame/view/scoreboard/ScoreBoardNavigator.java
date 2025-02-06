package de.cargame.view.scoreboard;

import de.cargame.view.ApiHandler;
import de.cargame.view.navigation.Navigator;
/**
 * Implements the methods provided by Navigator. Purpose is
 * interaction and navigation the button options in the scoreboard scene.
 */
class ScoreBoardNavigator extends Navigator {
    /**
     * Creates a new ScoreBoardNavigator instance, and passes a ApiHandler to it.
     */
    ScoreBoardNavigator(ApiHandler apiHandler) {
        super(apiHandler);
    }
}
