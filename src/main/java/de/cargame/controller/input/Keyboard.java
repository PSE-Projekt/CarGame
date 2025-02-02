package de.cargame.controller.input;

import com.github.kwhat.jnativehook.GlobalScreen;
import com.github.kwhat.jnativehook.keyboard.NativeKeyEvent;
import com.github.kwhat.jnativehook.keyboard.NativeKeyListener;
import de.cargame.model.entity.gameobject.interfaces.UserInputObserver;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Keyboard extends InputDevice implements NativeKeyListener {

    private final UserInputBundle userInputBundle = new UserInputBundle();
    private final List<UserInputObserver> userInputObservers;

    public Keyboard(String playerId) {
        super(playerId);
        userInputObservers = new ArrayList<>();

        GlobalScreen.addNativeKeyListener(this);
    }

    @Override
    public void registerObserver(UserInputObserver o) {
        userInputObservers.add(o);
    }

    @Override
    public void removeObserver(UserInputObserver o) {
        userInputObservers.remove(o);
    }

    @Override
    public void notifyObservers(UserInputBundle userInputBundle) {
        for (UserInputObserver userInputObserver : userInputObservers) {
            userInputObserver.update(userInputBundle);
        }
    }

    /**
     * Handles a native key press event from the keyboard. This method retrieves the key code
     * from the event and attempts to map it to a {@code UserInputType}. If a valid input type
     * is found, it is added to the {@code userInputBundle}, and all registered observers
     * are notified of the updated bundle state.
     *
     * @param e the {@code NativeKeyEvent} representing the key press event
     */
    @Override
    public void nativeKeyPressed(NativeKeyEvent e) {
        int keyCode = e.getKeyCode();
        Optional<UserInputType> userInputTypeOptional = UserInputType.getUserInputForKeyCode(keyCode);
        if (userInputTypeOptional.isPresent()) {
            UserInputType userInput = userInputTypeOptional.get();
            userInputBundle.addUserInput(userInput);
            notifyObservers(userInputBundle);
        }

        //NO VALID INPUT -> ignored
    }

    /**
     * Handles a native key release event from the keyboard. This method retrieves
     * the key code from the event and attempts to map it to a {@code UserInputType}.
     * If a matching input type is found, it is removed from the {@code userInputBundle}.
     * If the bundle becomes empty after this operation, it adds the {@code UserInputType.NONE}
     * to the bundle. Finally, all registered observers are notified of the updated bundle state.
     *
     * @param e the {@code NativeKeyEvent} representing the key release event
     */
    @Override
    public void nativeKeyReleased(NativeKeyEvent e) {
        int keyCode = e.getKeyCode();
        Optional<UserInputType> userInputTypeOptional = UserInputType.getUserInputForKeyCode(keyCode);
        userInputTypeOptional.ifPresent(userInputBundle::removeUserInput);
        if (userInputBundle.isEmpty()) {
            userInputBundle.addUserInput(UserInputType.NONE);
        }
        notifyObservers(userInputBundle);
    }
}
