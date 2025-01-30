package de.cargame.model.service;

import de.cargame.controller.input.GamePad;
import de.cargame.controller.input.Keyboard;
import de.cargame.model.entity.gameobject.interfaces.UserInputObserver;

public class InputService {

    private Keyboard keyboard;
    private GamePad gamePad;


    /**
     * Initializes the keyboard input device for a specific player.
     * Creates a Keyboard instance associated with the given player ID.
     *
     * @param playerId the identifier of the player for whom the keyboard is initialized
     */
    public void initKeyboard(String playerId) {
        this.keyboard = new Keyboard(playerId);
    }

    /**
     * Initializes the gamepad input device for a specific player.
     * Creates a GamePad instance associated with the given player ID.
     *
     * @param playerId the identifier of the player for whom the gamepad is initialized
     */
    public void initGamepad(String playerId) {
        this.gamePad = new GamePad(playerId);
    }


    /**
     * Registers a keyboard observer to monitor and handle keyboard-related user inputs.
     *
     * @param inputObserver the observer instance implementing the UserInputObserver interface,
     *                      which will be notified of keyboard input updates.
     */
    public void registerKeyboardObserver(UserInputObserver inputObserver) {
        this.keyboard.registerObserver(inputObserver);
    }

    /**
     * Registers an observer for the gamepad input device. The specified observer
     * will be notified when there are updates or changes in the gamepad's user input.
     *
     * @param inputObserver the observer instance implementing the UserInputObserver
     *                      interface, which will handle gamepad input updates.
     */
    public void registerGamePadObserver(UserInputObserver inputObserver) {
        this.gamePad.registerObserver(inputObserver);
    }

}
