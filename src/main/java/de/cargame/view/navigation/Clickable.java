package de.cargame.view.navigation;

import de.cargame.view.ApiHandler;
/**
 * Interface providing functions for behaviour upon Interaction.
 * Implemented by classes extending SceneButton.
 */
public interface Clickable {
    /**
     * Calls data that should be performed upon interaction with the button.
     */
    void onClick(ApiHandler apiHandler, String playerId);

}
