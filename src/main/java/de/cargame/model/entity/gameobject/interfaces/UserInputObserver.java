package de.cargame.model.entity.gameobject.interfaces;


import de.cargame.controller.input.UserInputBundle;

/**
 * Defines an observer interface for handling updates related to user inputs.
 * Classes implementing this interface are notified whenever the observed input source
 * generates or changes a set of user inputs.
 */
public interface UserInputObserver {

    /**
     * Handles updates related to a specific user input bundle. This method is invoked by
     * observed components to notify an implementation of changes or events associated with
     * user inputs.
     *
     * @param userInputBundle the bundle encapsulating a collection of user inputs to be processed
     */
    void update(UserInputBundle userInputBundle);
}
