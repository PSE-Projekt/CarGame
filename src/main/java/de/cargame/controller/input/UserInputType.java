package de.cargame.controller.input;

import lombok.Getter;

import java.util.Optional;

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

    public int getKeyCode() {
        return keyCode;
    }
}
