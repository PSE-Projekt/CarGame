package de.cargame.exception;

/**
 * Thrown to indicate that a game object has been assigned invalid bounds or state
 * that violates specific rules or constraints within the Car Game application.
 * <p>
 * This exception is a subclass of {@code CarGameException}, providing specific
 * context for issues related to illegal states or configurations of game objects.
 */
public class IllegalGameObjectBoundException extends CarGameException {
    public IllegalGameObjectBoundException(String message) {
        super(message);
    }
}
