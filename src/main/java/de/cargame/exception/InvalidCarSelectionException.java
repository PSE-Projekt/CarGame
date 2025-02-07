package de.cargame.exception;

/**
 * Thrown to indicate that a car selection made by a player is invalid or not permitted
 * in the Car Game application.
 * <p>
**/
public class InvalidCarSelectionException extends CarGameException {
    public InvalidCarSelectionException(String message) {
        super(message);
    }
}
