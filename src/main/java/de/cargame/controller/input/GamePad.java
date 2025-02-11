package de.cargame.controller.input;

import de.cargame.controller.input.gamepadmapping.GamePadMapping;
import de.cargame.controller.input.gamepadmapping.GamePads;
import de.cargame.model.entity.gameobject.interfaces.UserInputObserver;
import lombok.extern.slf4j.Slf4j;
import net.java.games.input.*;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Represents a gamepad input device. This class extends the {@code InputDevice} class
 * and implements functionality specific to handling gamepad inputs. It supports the
 * observer pattern for notifying registered observers about user input events.
 */
@Slf4j
public class GamePad extends InputDevice {

    private final List<UserInputObserver> userInputObserverList = new CopyOnWriteArrayList<>();
    private final UserInputBundle userInputBundle;
    ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
    private Controller gamepad;
    private GamePadMapping activeGamePadMapping;


    public GamePad() {
        userInputBundle = new UserInputBundle();
        init();
    }

    private void init() {
        gamepad = getGamepadController();

        if (gamepad == null) {
            log.info("No gamepad found - to connect a gamepad please connect it to the computer and restart the game");
            return;
        }

        // Scheduler für konstante Abfragen in regelmäßigen Abständen einrichten
        scheduler.scheduleAtFixedRate(() -> {
            try {
                gamepad.poll();
                EventQueue queue = gamepad.getEventQueue();
                Event event = new Event();

                while (queue.getNextEvent(event)) {
                    try {
                        Component[] components = gamepad.getComponents();
                        for (Component component : components) {
                            processInput(component);
                        }

                        // Benachrichtige die Observer nach der Eingabeverarbeitung
                        notifyObservers(userInputBundle);
                    } catch (Exception e) {
                        log.error("An error occurred while processing gamepad input", e);
                    }
                }
            } catch (Exception e) {
                log.error("An error occurred during gamepad polling", e);
            }
        }, 0, 5, TimeUnit.MILLISECONDS);
    }


    /**
     * Processes input received from a specific hardware component, such as a gamepad axis or button.
     * Determines the type of user input based on the polling data from the component and
     * updates the user input bundle accordingly.
     *
     * @param component The input hardware component being polled. This could be an axis, button, or any other
     *                  input-related component that provides polling data for determining user interactions.
     */
    private void processInput(Component component) {
        activeGamePadMapping = getGamePadMapping(gamepad.getName());
        float value = component.getPollData();
        final float DEADZONE = 0.15f;
        if (activeGamePadMapping.getX_AxisComponentName().equals(component.getName())) {
            if (Math.abs(value) < DEADZONE) { // Neutral
                userInputBundle.removeUserInput(UserInputType.LEFT);
                userInputBundle.removeUserInput(UserInputType.RIGHT);
                userInputBundle.addUserInput(UserInputType.NONE);
            } else if (value < -DEADZONE) { // Left
                userInputBundle.removeUserInput(UserInputType.RIGHT);
                userInputBundle.addUserInput(UserInputType.LEFT);
            } else if (value > DEADZONE) { // Right
                userInputBundle.removeUserInput(UserInputType.LEFT);
                userInputBundle.addUserInput(UserInputType.RIGHT);
            }
        }

        if (activeGamePadMapping.getY_AxisComponentName().equals(component.getName())) {
            if (Math.abs(value) < DEADZONE) { // Neutral
                userInputBundle.removeUserInput(UserInputType.UP);
                userInputBundle.removeUserInput(UserInputType.DOWN);
                userInputBundle.addUserInput(UserInputType.NONE);
            } else if (value < -DEADZONE) { // Up
                userInputBundle.removeUserInput(UserInputType.DOWN);
                userInputBundle.addUserInput(UserInputType.UP);
            } else if (value > DEADZONE) { // Down
                userInputBundle.removeUserInput(UserInputType.UP);
                userInputBundle.addUserInput(UserInputType.DOWN);
            }
        }

        // Example for a button (e.g., Sprint or Confirm)
        if (activeGamePadMapping.getFastForwardComponentName().equals(component.getName())) {
            // Press
            // Release
            userInputBundle.setFastForward(value > 0.5f);
        }
    }

    private Controller getGamepadController() {
        ControllerEnvironment ce = ControllerEnvironment.getDefaultEnvironment();

        for (Controller polledGamepad : ce.getControllers()) {
            log.info("Detected controller: " + polledGamepad.getName() + " (Type: " + polledGamepad.getType() + ")");
            if (polledGamepad.getType() == Controller.Type.STICK || polledGamepad.getType() == Controller.Type.GAMEPAD) {
                return polledGamepad;
            }
        }
        log.info("No compatible gamepad or stick found.");
        return null; // No gamepad found
    }

    protected GamePadMapping getGamePadMapping(String gamepadName) {
        GamePadMapping mapping = null;
        if (GamePads.XBOX_WIRELESS_CONTROLLER.getGamePadMapping().getControllerName().equals(gamepadName)) {
            mapping = GamePads.XBOX_WIRELESS_CONTROLLER.getGamePadMapping();
        } else if (GamePads.PS3_Controller.getGamePadMapping().getControllerName().equals(gamepadName)) {
            mapping = GamePads.PS3_Controller.getGamePadMapping();
        }
        if (mapping != null) {
            if (mapping != activeGamePadMapping) {
                log.info("Gamepad mapping found: {}", mapping);
            }
            return mapping;
        }
        return GamePads.XBOX_WIRELESS_CONTROLLER.getGamePadMapping();
    }

    @Override
    public void registerObserver(UserInputObserver o) {
        if (!userInputObserverList.contains(o)) {
            userInputObserverList.add(o);
        }
    }

    @Override
    public void removeObserver(UserInputObserver o) {
        userInputObserverList.remove(o);
    }

    @Override
    public void notifyObservers(UserInputBundle userInputBundle) {
        userInputObserverList.forEach(o -> o.update(userInputBundle));
    }
}
