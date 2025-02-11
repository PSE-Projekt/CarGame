package de.cargame.controller.input;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserInputBundleTest {

    @Test
    void testAddUserInput_AddsUniqueInputToList() {
        // Arrange
        UserInputBundle userInputBundle = new UserInputBundle();
        UserInputType inputType = UserInputType.UP;

        // Act
        userInputBundle.addUserInput(inputType);

        // Assert
        assertEquals(1, userInputBundle.size());
        assertTrue(userInputBundle.contains(inputType));
    }

    @Test
    void testAddUserInput_DoesNotAddDuplicateInput() {
        // Arrange
        UserInputBundle userInputBundle = new UserInputBundle();
        UserInputType inputType = UserInputType.UP;

        // Act
        userInputBundle.addUserInput(inputType);
        userInputBundle.addUserInput(inputType);

        // Assert
        assertEquals(1, userInputBundle.size());
        assertTrue(userInputBundle.contains(inputType));
    }

    @Test
    void testAddUserInput_ReplacesNoneInput() {
        // Arrange
        UserInputBundle userInputBundle = new UserInputBundle();
        UserInputType noneType = UserInputType.NONE;
        userInputBundle.addUserInput(noneType);

        // Act
        userInputBundle.addUserInput(UserInputType.LEFT);

        // Assert
        assertEquals(1, userInputBundle.size());
        assertFalse(userInputBundle.contains(noneType));
        assertTrue(userInputBundle.contains(UserInputType.LEFT));
    }

    @Test
    void testAddUserInput_SetsFastForwardOnConfirm() {
        // Arrange
        UserInputBundle userInputBundle = new UserInputBundle();
        UserInputType confirmType = UserInputType.CONFIRM;

        // Act
        userInputBundle.addUserInput(confirmType);

        // Assert
        assertTrue(userInputBundle.isFastForward());
        assertTrue(userInputBundle.contains(confirmType));
    }

    @Test
    void testAddUserInput_MultipleInputsAddedSuccessfully() {
        // Arrange
        UserInputBundle userInputBundle = new UserInputBundle();
        UserInputType input1 = UserInputType.UP;
        UserInputType input2 = UserInputType.LEFT;

        // Act
        userInputBundle.addUserInput(input1);
        userInputBundle.addUserInput(input2);

        // Assert
        assertEquals(2, userInputBundle.size());
        assertTrue(userInputBundle.contains(input1));
        assertTrue(userInputBundle.contains(input2));
    }
}