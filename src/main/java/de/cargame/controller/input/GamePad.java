package de.cargame.controller.input;

import de.cargame.controller.input.gamepadmapping.GamePadMapping;
import de.cargame.controller.input.gamepadmapping.GamePads;
import de.cargame.model.entity.gameobject.interfaces.UserInputObserver;
import lombok.extern.slf4j.Slf4j;
import net.java.games.input.Component;
import net.java.games.input.Controller;
import net.java.games.input.ControllerEnvironment;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Represents a gamepad input device. This class extends the {@code InputDevice} class
 * and implements functionality specific to handling gamepad inputs. It supports the
 * observer pattern for notifying registered observers about user input events.
 */
@Slf4j
public class GamePad extends InputDevice {

    private final List<UserInputObserver> userInputObserverList = new CopyOnWriteArrayList<>();
    private final UserInputBundle userInputBundle;
    private Controller gamepad;
    private GamePadMapping activeGamePadMapping;

    public GamePad() {
        userInputBundle = new UserInputBundle();
        init();
    }

    private void init() {
        // Start a new thread to poll for gamepad inputs
        new Thread(() -> {
            while (true) {
                // Ensure gamepad is initialized and available
                if (gamepad == null || !gamepad.poll()) {
                    gamepad = getGamepadController();

                    // Wait if no gamepad is connected
                    if (gamepad == null) {
                        log.info("No compatible gamepad detected. Retrying in 2 seconds...");
                        try {
                            Thread.sleep(2000);
                        } catch (InterruptedException e) {
                            log.error("Thread interrupted", e);
                        }
                        continue;
                    }
                }

                try {
                    Component[] components = gamepad.getComponents();
                    for (Component component : components) {
                        processInput(component);
                        notifyObservers(userInputBundle);
                    }
                } catch (Exception e) {
                    log.error("An error occurred while processing gamepad input", e);
                }

                try {
                    Thread.sleep(5); // Prevent high CPU usage
                } catch (InterruptedException e) {
                    log.error("Thread interrupted", e);
                }
            }
        }).start();
    }

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
            if (value > 0.5f) { // Press
                userInputBundle.setFastForward(true);
            } else { // Release
                userInputBundle.setFastForward(false);
                userInputBundle.removeUserInput(UserInputType.CONFIRM);
            }
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

    private GamePadMapping getGamePadMapping(String gamepadName) {
        GamePadMapping mapping = null;
        if (GamePads.XBOX_WIRELESS_CONTROLLER.getGamePadMapping().getControllerName().equals(gamepadName)) {
            mapping = GamePads.XBOX_WIRELESS_CONTROLLER.getGamePadMapping();
        }
        if (mapping != null) {
            if(mapping != activeGamePadMapping){
                log.info("Gamepad mapping found: {}", mapping);
            }
            return mapping;
        }
        log.warn("No gamepad mapping found for gamepad {} - resume to default", gamepadName);
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
