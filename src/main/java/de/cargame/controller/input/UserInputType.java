package de.cargame.controller.input;

import lombok.Getter;

import java.util.Optional;

/**
 * Represents the types of user input actions recognized by the system.
 * Each enum constant is mapped to a specific key code that represents
 * a corresponding input action.
 *
 * The user input types include:
 * - UP: Represents upward movement input.
 * - LEFT: Represents leftward movement input.
 * - DOWN: Represents downward movement input.
 * - RIGHT: Represents rightward movement input.
 * - CONFIRM: Represents an action to confirm a selection and fast-forward.
 * - NONE: Represents no valid user input.
 *
 * This enum provides functionality to retrieve a specific
 * {@code UserInputType} corresponding to a given key code.
 */
@Getter
public enum UserInputType {
    UP(17),
    LEFT(30),
    DOWN(31),
    RIGHT(32),
    CONFIRM(28),
    NONE(-1);


    private final int keyCode;


    UserInputType(int keyCode) {
        this.keyCode = keyCode;
    }

    public static Optional<UserInputType> getUserInputForKeyCode(int keyCode) {
        UserInputType[] values = values();
        for (UserInputType value : values) {
            if (value.getKeyCode() == keyCode) {
                return Optional.of(value);
            }
        }
        return Optional.empty();
    }

}
