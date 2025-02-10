package de.cargame.controller.input;

import de.cargame.model.entity.gameobject.interfaces.UserInputObserver;
import lombok.extern.slf4j.Slf4j;
import net.java.games.input.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * The Keyboard class represents a keyboard input device. It extends the InputDevice class and
 * implements the NativeKeyListener interface to listen for native keyboard events. The class
 * manages user input and supports an observer pattern to notify registered observers of
 * input changes.
 * <p>
 * This class processes native key press and key release events, translates them into
 * application-specific user inputs using the UserInputType enum, and updates the state of the
 * UserInputBundle accordingly. Observers are notified whenever the input bundle changes.
 */
@Slf4j
public class Keyboard extends InputDevice {

    private final UserInputBundle userInputBundle;
    private final List<UserInputObserver> userInputObservers;
    Controller keyboard;

    public Keyboard() {
        userInputObservers = new ArrayList<>();
        userInputBundle = new UserInputBundle();
        keyboard = findKeyboard();
        processKeyboardInput();
    }

    private Controller findKeyboard()
    {
        ControllerEnvironment controllerEnvironment =ControllerEnvironment.getDefaultEnvironment();
        Controller[] aController = controllerEnvironment.getControllers();
        for (Controller controller : aController) {
            Component[] components = controller.getComponents();
            boolean isKeyboard = Arrays.stream(components).anyMatch(component -> component.getIdentifier().getName().equals("Escape"));

            if (controller.getType() == Controller.Type.KEYBOARD || isKeyboard) {
                return controller;
            }
        }
        return null;
    }

    @Override
    public void registerObserver(UserInputObserver o) {
        if(!userInputObservers.contains(o)){
            userInputObservers.add(o);
        }
    }

    @Override
    public void removeObserver(UserInputObserver o) {
        if(!userInputObservers.contains(o)) {
            userInputObservers.remove(o);
        }
    }

    @Override
    public void notifyObservers(UserInputBundle userInputBundle) {
        for (UserInputObserver userInputObserver : userInputObservers) {
            userInputObserver.update(userInputBundle);
        }
    }


    private void processKeyboardInput() {
        new Thread(()->{
            while (true) {
                keyboard.poll();
                EventQueue queue = keyboard.getEventQueue();
                Event event = new Event();

                while (queue.getNextEvent(event)) {
                    Component component = event.getComponent();
                    char key = component.getName().toLowerCase().charAt(0);
                    Optional<UserInputType> userInputTypeOptional = UserInputType.getUserInputForKeyCode(key);

                    if(userInputTypeOptional.isPresent()){
                        UserInputType userInput = userInputTypeOptional.get();
                        float value = event.getValue();
                        if(value == 1.0){
                            userInputBundle.addUserInput(userInput);
                            notifyObservers(userInputBundle);
                        }else if(value == 0.0){
                            userInputTypeOptional.ifPresent(userInputBundle::removeUserInput);
                            if (userInputBundle.isEmpty()) {
                                userInputBundle.addUserInput(UserInputType.NONE);
                            }
                        }
                        notifyObservers(userInputBundle);
                    }
                }
            }
        }).start();

    }
}
