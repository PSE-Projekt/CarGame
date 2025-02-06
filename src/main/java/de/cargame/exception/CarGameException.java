package de.cargame.exception;

public abstract class CarGameException extends RuntimeException {

    protected CarGameException(String message) {
        super(message);
    }
}
