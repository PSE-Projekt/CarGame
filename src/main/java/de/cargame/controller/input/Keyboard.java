package de.cargame.controller.input;

import de.cargame.model.entity.gameobject.interfaces.UserInputObserver;
import lombok.extern.slf4j.Slf4j;
import net.java.games.input.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * The Keyboard class represents a keyboard input device.
 * It processes key press and release events and notifies observers of input changes.
 */
@Slf4j
public class Keyboard extends InputDevice {

    private final UserInputBundle userInputBundle;
    private final List<UserInputObserver> userInputObservers;
    private final ScheduledExecutorService scheduler;
    private Controller keyboard;

    public Keyboard() {
        this.userInputBundle = new UserInputBundle();
        this.userInputObservers = new ArrayList<>();
        this.scheduler = Executors.newSingleThreadScheduledExecutor();
        this.keyboard = findKeyboard();

        if (this.keyboard == null) {
            log.error("No keyboard found!");
        } else {
            startKeyboardProcessing();
        }
    }

    /**
     * Attempts to find a keyboard controller from the system's available controllers.
     *
     * @return The keyboard controller, or null if not found.
     */
    private Controller findKeyboard() {
        for (Controller controller : ControllerEnvironment.getDefaultEnvironment().getControllers()) {
            if (controller.getType() == Controller.Type.KEYBOARD) {
                log.info("Keyboard found: {}", controller.getName());
                return controller;
            }
        }
        return null;
    }

    /**
     * Starts processing keyboard input at fixed intervals using the ScheduledExecutorService.
     */
    protected void startKeyboardProcessing() {
        scheduler.scheduleAtFixedRate(() -> {
            try {
                processKeyboardInput();
            } catch (Exception e) {
                log.error("Error processing keyboard input", e);
            }
        }, 0, 5, TimeUnit.MILLISECONDS); // Polling every 5 milliseconds
    }


    /**
     * Processes input events received from a keyboard device, managing user input states
     * and notifying observers about changes in user input. This method handles the polling
     * of the keyboard, processes each input event from the event queue, and updates the
     * corresponding user input state in the `userInputBundle`.
     */
    private void processKeyboardInput() {
        if (keyboard == null) {
            log.warn("No keyboard available to process input.");
            return;
        }

        keyboard.poll();
        EventQueue queue = keyboard.getEventQueue();
        Event event = new Event();

        while (queue.getNextEvent(event)) {
            Component component = event.getComponent();
            String componentName = component.getName();

            if (componentName != null && !componentName.isBlank()) {
                log.debug("Processing keyboard input: {}", componentName);

                char key = componentName.toLowerCase().charAt(0);
                Optional<UserInputType> userInputTypeOptional = UserInputType.getUserInputForKeyCode(key);

                if (userInputTypeOptional.isPresent()) {
                    UserInputType userInput = userInputTypeOptional.get();
                    float value = event.getValue();

                    if (value == 1.0) { // Key pressed
                        userInputBundle.addUserInput(userInput);
                    } else if (value == 0.0) { // Key released
                        userInputBundle.removeUserInput(userInput);

                        // Ensure at least NONE is present if no input remains
                        if (userInputBundle.isEmpty() && !userInputBundle.contains(UserInputType.NONE)) {
                            userInputBundle.addUserInput(UserInputType.NONE);
                        }
                    }

                    notifyObservers(userInputBundle);
                }
            }
        }
    }

    @Override
    public void registerObserver(UserInputObserver o) {
        if (!userInputObservers.contains(o)) {
            userInputObservers.add(o);
        }
    }

    @Override
    public void removeObserver(UserInputObserver o) {
        userInputObservers.remove(o);
    }

    @Override
    public void notifyObservers(UserInputBundle userInputBundle) {
        for (UserInputObserver observer : userInputObservers) {
            observer.update(userInputBundle);
        }
    }
}