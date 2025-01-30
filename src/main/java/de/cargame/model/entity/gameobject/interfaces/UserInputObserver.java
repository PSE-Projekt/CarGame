package de.cargame.model.entity.gameobject.interfaces;


import de.cargame.controller.input.UserInputBundle;

/**
 * Defines an observer interface for handling updates related to user inputs.
 * Classes implementing this interface are notified whenever the observed input source
 * generates or changes a set of user inputs.
 */
public interface UserInputObserver {

    void update(UserInputBundle userInputBundle);
}
