package de.cargame.exception;

public abstract class CarGameException extends RuntimeException {

    public CarGameException(String message) {
        super(message);
    }
}
