package de.cargame.exception;

/**
 * Thrown to indicate an issue related to loading or processing a sound file
 * in the Car Game application.
 * <p>
 * This exception is a subclass of {@code CarGameException}, designed to
 * provide a specific context for errors arising from operations such as
 * locating, accessing, or reading sound files used within the game.
 * <p>
 * Typically, this exception is used when a sound file is missing, corrupted,
 * or incompatible with the expected format, making it unable to be processed
 * or played during the game's execution.
 */
public class SoundFileException extends CarGameException {
    public SoundFileException(String message) {
        super(message);
    }
}
