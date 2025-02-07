package de.cargame.exception;

/**
 * Represents a base class for exceptions that occur in the Car Game application.
 * This abstract class extends {@code RuntimeException} and serves as a common
 * parent for all custom exceptions specific to the Car Game domain.
 * <p>
 * Subclasses of {@code CarGameException} are used to denote specific error
 * situations in the game, such as invalid player actions, illegal game object
 * states, or incorrect selections. Each subclass provides a distinct context and
 * meaning to the exception being thrown.
 */
public abstract class CarGameException extends RuntimeException {

    protected CarGameException(String message) {
        super(message);
    }
}
