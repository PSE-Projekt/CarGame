package de.cargame.controller.input;

import de.cargame.controller.input.gamepadmapping.GamePadMapping;
import de.cargame.controller.input.gamepadmapping.GamePads;
import de.cargame.model.entity.gameobject.interfaces.UserInputObserver;
import net.java.games.input.Controller;
import net.java.games.input.Rumbler;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.mockito.Mockito;


import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class GamePadTest {



    @Test
    void testNotifyObservers_withMultipleObserversAfterRemovalAndRepeat() {
        // Arrange
        GamePad gamePad = new GamePad();
        UserInputObserver observer1 = Mockito.mock(UserInputObserver.class);
        UserInputObserver observer2 = Mockito.mock(UserInputObserver.class);
        UserInputBundle userInputBundle = new UserInputBundle();

        gamePad.registerObserver(observer1);
        gamePad.registerObserver(observer2);

        gamePad.removeObserver(observer2);

        // Act
        gamePad.notifyObservers(userInputBundle);
        gamePad.notifyObservers(userInputBundle);

        // Assert
        verify(observer1, times(2)).update(userInputBundle);
        verify(observer2, never()).update(userInputBundle);
    }

    @Test
    void testNotifyObservers_withMultipleObservers() {
        // Arrange
        GamePad gamePad = new GamePad();
        UserInputObserver observer1 = Mockito.mock(UserInputObserver.class);
        UserInputObserver observer2 = Mockito.mock(UserInputObserver.class);
        UserInputBundle userInputBundle = new UserInputBundle();

        gamePad.registerObserver(observer1);
        gamePad.registerObserver(observer2);

        // Act
        gamePad.notifyObservers(userInputBundle);

        // Assert
        verify(observer1, times(1)).update(userInputBundle);
        verify(observer2, times(1)).update(userInputBundle);
    }

    @Test
    void testNotifyObservers_withNoObservers() {
        // Arrange
        GamePad gamePad = new GamePad();
        UserInputBundle userInputBundle = new UserInputBundle();

        // Assert
        assertDoesNotThrow(() -> gamePad.notifyObservers(userInputBundle));
    }

    @Test
    void testNotifyObservers_afterObserverRemoval() {
        // Arrange
        GamePad gamePad = new GamePad();
        UserInputObserver observer = Mockito.mock(UserInputObserver.class);
        UserInputBundle userInputBundle = new UserInputBundle();

        gamePad.registerObserver(observer);
        gamePad.removeObserver(observer);

        // Act
        gamePad.notifyObservers(userInputBundle);

        // Assert
        verify(observer, never()).update(userInputBundle);
    }


    @Test
    void testNotifyObservers_propagatesUpdatedStateToObserver() {
        // Arrange
        GamePad gamePad = new GamePad();
        UserInputObserver observer = Mockito.mock(UserInputObserver.class);
        UserInputBundle userInputBundle = new UserInputBundle();

        gamePad.registerObserver(observer);

        // Act
        userInputBundle.addUserInput(UserInputType.UP);
        gamePad.notifyObservers(userInputBundle);

        // Assert
        verify(observer).update(userInputBundle);
    }

    @Test
    void testNotifyObservers_withDuplicateObservers() {
        // Arrange
        GamePad gamePad = new GamePad();
        UserInputObserver observer = Mockito.mock(UserInputObserver.class);
        UserInputBundle userInputBundle = new UserInputBundle();

        gamePad.registerObserver(observer);
        gamePad.registerObserver(observer);

        // Act
        gamePad.notifyObservers(userInputBundle);

        // Assert
        verify(observer, times(1)).update(userInputBundle);
    }

    @Test
    void testNotifyObservers_withEmptyObserverList() {
        // Arrange
        GamePad gamePad = new GamePad();
        UserInputBundle userInputBundle = new UserInputBundle();

        // Act and Assert
        assertDoesNotThrow(() -> gamePad.notifyObservers(userInputBundle),
                "notifyObservers should not throw an exception when there are no observers.");
    }


    @Test
    void testRetrieveGamePadMappingsByName() {
        // Arrange
        String gamePadName = "Xbox Wireless Controller";
        GamePad gamePad = new GamePad();

        GamePadMapping gamePadMapping = gamePad.getGamePadMapping(gamePadName);

        assertEquals(gamePadName, gamePadMapping.getControllerName());

    }

    @Test
    void testRumble_withGamepad() throws NoSuchFieldException, IllegalAccessException {
        // Arrange
        GamePad gamePad = new GamePad();
        Controller mockController = Mockito.mock(Controller.class);
        Rumbler mockRumbler = Mockito.mock(Rumbler.class);

        when(mockController.getRumblers()).thenReturn(new Rumbler[]{mockRumbler});

        Field gamepadField = GamePad.class.getDeclaredField("gamepad");
        gamepadField.setAccessible(true);
        gamepadField.set(gamePad, mockController);

        // Act
        gamePad.rumble(0.5f);

        // Assert
        verify(mockRumbler, times(1)).rumble(0.5f);
    }

    @Test
    void testRumble_multipleRumblers() throws NoSuchFieldException, IllegalAccessException {
        // Arrange
        GamePad gamePad = new GamePad();
        Controller mockController = Mockito.mock(Controller.class);
        Rumbler mockRumbler1 = Mockito.mock(Rumbler.class);
        Rumbler mockRumbler2 = Mockito.mock(Rumbler.class);

        when(mockController.getRumblers()).thenReturn(new Rumbler[]{mockRumbler1, mockRumbler2});

        Field gamepadField = GamePad.class.getDeclaredField("gamepad");
        gamepadField.setAccessible(true);
        gamepadField.set(gamePad, mockController);

        // Act
        gamePad.rumble(0.75f);

        // Assert
        verify(mockRumbler1, times(1)).rumble(0.75f);
        verify(mockRumbler2, times(1)).rumble(0.75f);
    }

    @Test
    void testRumble_noGamepad() {
        // Arrange
        GamePad gamePad = new GamePad();

        // Act and Assert
        assertDoesNotThrow(() -> gamePad.rumble(0.5f), "Rumble should not throw an exception if no gamepad is connected.");
    }

}