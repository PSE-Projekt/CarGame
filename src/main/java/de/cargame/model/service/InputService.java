package de.cargame.model.service;

import de.cargame.controller.input.GamePad;
import de.cargame.controller.input.Keyboard;
import de.cargame.model.entity.gameobject.interfaces.UserInputObserver;

/**
 * This class provides input services for managing and initializing user input devices,
 * such as keyboards and gamepads, and also enables observer-based input handling.
 * It acts as a high-level abstraction for input management within the application.
 */
public class InputService {

    private Keyboard keyboard;
    private GamePad gamePad;


    /**
     * Initializes the keyboard input device for a specific player.
     * Creates a Keyboard instance associated with the given player ID.
     */
    public void initKeyboard() {
        this.keyboard = new Keyboard();
    }

    /**
     * Initializes the gamepad input device for a specific player.
     * Creates a GamePad instance associated with the given player ID.
     */
    public void initGamepad() {
        this.gamePad = new GamePad();
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


    /**
     * Triggers the rumbling effect on the connected gamepad with the specified intensity.
     * This method activates available rumblers on the gamepad to provide haptic feedback,
     * enhancing the user's experience during gameplay or interactive scenarios.
     *
     * @param intensity the intensity of the rumble effect, where 0.0 represents no rumble
     *                  and 1.0 represents full intensity.
     */
    public void rumble(float intensity){
        this.gamePad.rumble(intensity);
    }

}
