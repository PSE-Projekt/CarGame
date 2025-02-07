package de.cargame.exception;

/**
 * Thrown to indicate that a specific player could not be found during a game operation
 * in the Car Game application.
 *
 * This exception is a subclass of {@code CarGameException}, providing a more specific
 * context for errors related to player retrieval or management. It is typically used
 * when attempting to access a player by their identification or role (e.g., keyboard
 * player, gamepad player) fails because the player has not been created or registered
 * in the game environment.
 */
public class PlayerNotFoundException extends CarGameException {
    public PlayerNotFoundException(String message) {
        super(message);
    }
}
