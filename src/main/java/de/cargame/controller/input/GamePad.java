package de.cargame.controller.input;

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
    private Controller controller;

    public GamePad() {
        userInputBundle = new UserInputBundle();
        init();
    }

    private void init() {
        this.controller = getGamepadController(); // Ruft den Gamepad-Controller ab

        if (this.controller == null) {
            throw new IllegalStateException("No gamepad controller found");
        }

        // Thread zum Abfragen des Gamepads starten
        new Thread(() -> {
            while (true) {
                // Poll für Gamepad-Eingaben
                controller.poll();
                Component[] components = controller.getComponents();

                for (Component component : components) {
                    // Erfassen des aktuellen Wertes
                    float value = component.getPollData();
                    final float DEADZONE = 0.2f; // Deadzone-Wert

                    // Deadzone-Logik für Achsen
                    if ("x".equals(component.getName())) {
                        if (Math.abs(value) < DEADZONE) { // Neutraler Zustand
                            userInputBundle.removeUserInput(UserInputType.LEFT);
                            userInputBundle.removeUserInput(UserInputType.RIGHT);
                            userInputBundle.addUserInput(UserInputType.NONE);
                        } else if (value < -DEADZONE) { // Bewegung nach links
                            userInputBundle.removeUserInput(UserInputType.RIGHT);
                            userInputBundle.addUserInput(UserInputType.LEFT);
                        } else if (value > DEADZONE) { // Bewegung nach rechts
                            userInputBundle.removeUserInput(UserInputType.LEFT);
                            userInputBundle.addUserInput(UserInputType.RIGHT);
                        }
                    }

                    if ("y".equals(component.getName())) {
                        if (Math.abs(value) < DEADZONE) { // Neutraler Zustand
                            userInputBundle.removeUserInput(UserInputType.UP);
                            userInputBundle.removeUserInput(UserInputType.DOWN);
                            userInputBundle.addUserInput(UserInputType.NONE);
                        } else if (value < -DEADZONE) { // Bewegung nach oben
                            userInputBundle.removeUserInput(UserInputType.DOWN);
                            userInputBundle.addUserInput(UserInputType.UP);
                        } else if (value > DEADZONE) { // Bewegung nach unten
                            userInputBundle.removeUserInput(UserInputType.UP);
                            userInputBundle.addUserInput(UserInputType.DOWN);
                        }
                    }

                    // Beispiel für einen Button (z. B. Sprinten oder Bestätigen)
                    if ("14".equals(component.getName())) {
                        if (value > 0.5f) { // Wenn Taste gedrückt ist
                            userInputBundle.setFastForward(true);
                        } else { // Wenn Taste losgelassen wird
                            userInputBundle.setFastForward(false);
                            userInputBundle.removeUserInput(UserInputType.CONFIRM);
                        }
                    }

                    // Benachrichtige alle Observer über Änderungen
                    notifyObservers(userInputBundle);

                    try {
                        Thread.sleep(10); // Reduziere CPU-Last durch Sleep
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

    private Controller getGamepadController() {
        ControllerEnvironment ce = ControllerEnvironment.getDefaultEnvironment();
        for (Controller gamepad : ce.getControllers()) {
            log.info("Detected controller: " + gamepad.getName() + " (Type: " + gamepad.getType() + ")");
            if (gamepad.getType() == Controller.Type.STICK || gamepad.getType() == Controller.Type.GAMEPAD) {
                return gamepad;
            }
        }
        log.info("No compatible gamepad or stick found.");
        return null; // No gamepad found
    }

    @Override
    public void registerObserver(UserInputObserver o) {
        userInputObserverList.add(o);
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
