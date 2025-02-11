package de.cargame.controller.input;

import de.cargame.model.entity.gameobject.interfaces.UserInputObserver;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.*;

class KeyboardTest {

    @Test
    void testRegisterObserver_AddsObserverSuccessfully() {
        // Arrange
        Keyboard keyboard = new Keyboard();
        UserInputObserver observer = Mockito.mock(UserInputObserver.class);

        // Act
        keyboard.registerObserver(observer);

        // Assert
        keyboard.notifyObservers(new UserInputBundle());
        verify(observer, times(1)).update(any(UserInputBundle.class));
    }

    @Test
    void testRegisterObserver_MultipleObserversReceiveUpdates() {
        // Arrange
        Keyboard keyboard = new Keyboard();
        UserInputObserver observer1 = Mockito.mock(UserInputObserver.class);
        UserInputObserver observer2 = Mockito.mock(UserInputObserver.class);

        // Act
        keyboard.registerObserver(observer1);
        keyboard.registerObserver(observer2);
        keyboard.notifyObservers(new UserInputBundle());

        // Assert
        verify(observer1, times(1)).update(any(UserInputBundle.class));
        verify(observer2, times(1)).update(any(UserInputBundle.class));
    }

    @Test
    void testRegisterObserver_ObserverDoesNotReceiveUpdateIfNotRegistered() {
        // Arrange
        Keyboard keyboard = new Keyboard();
        UserInputObserver registeredObserver = Mockito.mock(UserInputObserver.class);
        UserInputObserver unregisteredObserver = Mockito.mock(UserInputObserver.class);

        // Act
        keyboard.registerObserver(registeredObserver);
        keyboard.notifyObservers(new UserInputBundle());

        // Assert
        verify(registeredObserver, times(1)).update(any(UserInputBundle.class));
        verify(unregisteredObserver, never()).update(any(UserInputBundle.class));
    }

    @Test
    void testRegisterObserver_DoesNotAllowDuplicateObservers() {
        // Arrange
        Keyboard keyboard = new Keyboard();
        UserInputObserver observer = Mockito.mock(UserInputObserver.class);

        // Act
        keyboard.registerObserver(observer);
        keyboard.registerObserver(observer);
        keyboard.notifyObservers(new UserInputBundle());

        // Assert
        verify(observer, times(1)).update(any(UserInputBundle.class));
    }

    @Test
    void testRegisterObserver_NullObserverDoesNotThrowException() {
        // Arrange
        Keyboard keyboard = new Keyboard();

        // Act and Assert
        assertDoesNotThrow(() -> keyboard.registerObserver(null));
    }

    @Test
    void testNotifyObservers_WithNoRegisteredObservers() {
        // Arrange
        Keyboard keyboard = new Keyboard();

        // Act and Assert
        assertDoesNotThrow(() -> keyboard.notifyObservers(new UserInputBundle()));
    }

    @Test
    void testNotifyObservers_CallsUpdateOnAllRegisteredObservers() {
        // Arrange
        Keyboard keyboard = new Keyboard();
        UserInputObserver observer1 = Mockito.mock(UserInputObserver.class);
        UserInputObserver observer2 = Mockito.mock(UserInputObserver.class);
        keyboard.registerObserver(observer1);
        keyboard.registerObserver(observer2);
        UserInputBundle userInputBundle = new UserInputBundle();

        // Act
        keyboard.notifyObservers(userInputBundle);

        // Assert
        verify(observer1, times(1)).update(userInputBundle);
        verify(observer2, times(1)).update(userInputBundle);
    }

    @Test
    void testNotifyObservers_HandlesNullUserInputBundleGracefully() {
        // Arrange
        Keyboard keyboard = new Keyboard();
        UserInputObserver observer = Mockito.mock(UserInputObserver.class);
        keyboard.registerObserver(observer);

        // Act and Assert
        assertDoesNotThrow(() -> keyboard.notifyObservers(null));
    }

    @Test
    void testKeyBoardInput() {
        // Arrange
        Keyboard keyboard = Mockito.spy(new Keyboard());
        UserInputObserver observer = Mockito.mock(UserInputObserver.class);
        keyboard.registerObserver(observer);
        UserInputBundle userInputBundle = new UserInputBundle();

        // Simulate user input
        Mockito.doNothing().when(keyboard).startKeyboardProcessing();
        UserInputType userInput = UserInputType.UP; // Example input type
        userInputBundle.addUserInput(userInput);

        // Act
        keyboard.notifyObservers(userInputBundle);

        // Assert
        verify(observer, times(1)).update(userInputBundle);
    }


}