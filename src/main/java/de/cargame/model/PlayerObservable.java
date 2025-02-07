package de.cargame.model;

import de.cargame.model.entity.player.PlayerObserver;
import de.cargame.model.entity.player.PlayerUpdate;

/**
 * The PlayerObservable interface defines the contract for managing a list of
 * PlayerObserver instances and providing mechanisms to notify them whenever
 * there is a change in the player's state. This interface follows the observer
 * design pattern and facilitates a decoupled communication between the subject
 * (observable) and its observers.
 * <p>
 * Implementations of this interface are typically responsible for maintaining
 * the internal list of observers and ensuring that state changes are
 * consistently communicated to all registered observers.
 */
public interface PlayerObservable {

    /**
     * Registers a PlayerObserver to the observable instance. Once registered, the provided
     * PlayerObserver will receive notifications whenever there are updates or changes in the
     * player's state. This method allows the observable to maintain a collection of observers
     * for the purpose of broadcasting state changes.
     *
     * @param playerObserver the observer instance implementing the PlayerObserver interface
     *                       that will receive updates about the player's state changes.
     */
    void addObserver(PlayerObserver playerObserver);

    /**
     * Unregisters a previously added observer from receiving updates related to the player's state.
     * This method removes the provided PlayerObserver instance from the list of observers managed
     * by the implementing class, ensuring it will no longer be notified of changes.
     *
     * @param observer the PlayerObserver instance to be removed. If the observer is not currently
     *                 registered, this method has no effect.
     */
    void removeObserver(PlayerObserver observer);

    /**
     * Notifies all registered observers with the latest state of the player by
     * providing a PlayerUpdate instance. This method is part of the observer
     * design pattern and allows for decoupled communication between the subject
     * (observable) and its observers.
     *
     * @param playerUpdate The PlayerUpdate object containing the current state
     *                     of the player, including the player's unique identifier,
     *                     score, and remaining lives.
     */
    void notifyObservers(PlayerUpdate playerUpdate);
}
