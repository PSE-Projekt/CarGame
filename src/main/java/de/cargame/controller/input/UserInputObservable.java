package de.cargame.controller.input;


import de.cargame.model.entity.gameobject.interfaces.UserInputObserver;

/**
 * Represents a subject in the observer pattern specifically designed for handling user input events.
 * This interface allows registration, removal, and notification of observers interested in user input updates.
 * It is intended to be implemented by input-handling classes that deal with user input sources and
 * need to notify observers when input events occur.
 * <p>
 * Responsibilities:
 * 1. Maintain a list of observers that wish to monitor user input changes.
 * 2. Provide mechanisms for registering and removing observers from the notification list.
 * 3. Notify all registered observers when a user input event is captured through the `notifyObservers` method.
 * <p>
 * Observers implementing the {@code UserInputObserver} interface will be notified using the
 * {@code update} method, and the notification includes a {@code UserInputBundle} object
 * that carries the relevant user input data.
 */
public interface UserInputObservable {

    void registerObserver(UserInputObserver o);

    void removeObserver(UserInputObserver o);

    void notifyObservers(UserInputBundle userInputBundle);

}
