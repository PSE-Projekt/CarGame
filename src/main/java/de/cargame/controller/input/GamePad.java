package de.cargame.controller.input;

import de.cargame.model.entity.gameobject.interfaces.UserInputObserver;
import net.java.games.input.Controller;
import net.java.games.input.ControllerEnvironment;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Represents a gamepad input device. This class extends the {@code InputDevice} class
 * and implements functionality specific to handling gamepad inputs. It supports the
 * observer pattern for notifying registered observers about user input events.
 */
public class GamePad extends InputDevice {

    private final List<UserInputObserver> userInputObserverList = new CopyOnWriteArrayList<>();
    private boolean deviceConnected = false;
    private Controller controller;

    public GamePad(String id) {
        super(id);
        initController();
    }

    public void initController() {

        Controller[] controllers = ControllerEnvironment.getDefaultEnvironment().getControllers();

        for (Controller controller : controllers) {
            if (controller.getType() == Controller.Type.GAMEPAD) {
                this.controller = controller;
                deviceConnected = true;
                break;
            }
        }
        if (!deviceConnected) {
            System.out.println("No Gamepad found");
        }
    }

    public void readInput() {
    }


    public void processInput() {
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
