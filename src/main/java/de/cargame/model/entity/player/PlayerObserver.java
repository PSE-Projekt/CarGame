package de.cargame.model.entity.player;


/**
 * The PlayerObserver interface defines the contract for classes that need to observe changes
 * in a player's state within a game. This interface follows the observer design pattern,
 * allowing implementing classes to be notified whenever a player's state is updated.
 * <p>
 * Methods:
 * - update(PlayerUpdate playerUpdate): This method is called to notify the observer about
 * changes in a player's state. The changes are encapsulated in a PlayerUpdate object,
 * which provides details such as the player's ID, score, and remaining lives.
 * <p>
 * Implementing classes can use this interface to respond to player state changes,
 * for example, updating UI elements (e.g., scoreboards) or triggering game events.
 */
public interface PlayerObserver {

    void update(PlayerUpdate playerUpdate);

}
