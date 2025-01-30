package de.cargame.controller.input;

public abstract class InputDevice implements UserInputObservable {

    private final String playerid;

    public InputDevice(String playerid) {
        this.playerid = playerid;
    }
}
