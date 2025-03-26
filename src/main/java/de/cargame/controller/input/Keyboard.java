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

    private static final float KEY_PRESSED = 1.0f;
    private static final float KEY_RELEASED = 0.0f;
    private final UserInputBundle userInputBundle;
    private final List<UserInputObserver> userInputObservers;
    private final ScheduledExecutorService scheduler;
    private Controller jinputKeyboard;


    public Keyboard() {
        this.userInputBundle = new UserInputBundle();
        this.userInputObservers = new ArrayList<>();
        this.scheduler = Executors.newSingleThreadScheduledExecutor();
        this.jinputKeyboard = findKeyboard();

        if (this.jinputKeyboard == null) {
            log.warn("No keyboard found!");
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
        if (jinputKeyboard == null) {
            log.warn("No keyboard available to process input.");
            return;
        }

        jinputKeyboard.poll();
        EventQueue eventQueue = jinputKeyboard.getEventQueue();
        Event event = new Event();

        while (eventQueue.getNextEvent(event)) {
            handleKeyboardEvent(event);
        }
    }

    private void handleKeyboardEvent(Event event) {
        Component component = event.getComponent();
        String componentName = component.getName();

        if (isInvalidComponentName(componentName)) {
            return;
        }

        log.debug("Processing keyboard input: {}", componentName);
        char keyChar = componentName.toLowerCase().charAt(0);
        Optional<UserInputType> userInputTypeOptional = UserInputType.getUserInputForKeyCode(keyChar);

        userInputTypeOptional.ifPresent(userInput -> processUserInput(event, userInput));
    }

    private void processUserInput(Event event, UserInputType userInput) {
        float eventValue = event.getValue();

        if (isKeyPressed(eventValue)) {
            userInputBundle.addUserInput(userInput);
        } else if (isKeyReleased(eventValue)) {
            userInputBundle.removeUserInput(userInput);
            ensureNoneIsPresentIfEmpty();
        }

        notifyObservers(userInputBundle);
    }

    private boolean isKeyPressed(float value) {
        return value == KEY_PRESSED;
    }

    private boolean isKeyReleased(float value) {
        return value == KEY_RELEASED;
    }

    private boolean isInvalidComponentName(String name) {
        return name == null || name.isBlank();
    }

    private void ensureNoneIsPresentIfEmpty() {
        if (userInputBundle.isEmpty() && !userInputBundle.contains(UserInputType.NONE)) {
            userInputBundle.addUserInput(UserInputType.NONE);
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