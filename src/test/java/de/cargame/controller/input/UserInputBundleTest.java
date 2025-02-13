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
    void testAddUserInput_DoesNothingWhenNull() {
        // Arrange
        UserInputBundle userInputBundle = new UserInputBundle();

        // Act
        userInputBundle.addUserInput(null);

        // Assert
        assertEquals(0, userInputBundle.size());
        assertTrue(userInputBundle.isEmpty());
    }

    @Test
    void testAddUserInput_AddsConfirmTwice() {
        // Arrange
        UserInputBundle userInputBundle = new UserInputBundle();

        // Act
        userInputBundle.addUserInput(UserInputType.CONFIRM);
        userInputBundle.addUserInput(UserInputType.CONFIRM);

        // Assert
        assertEquals(1, userInputBundle.size());
        assertTrue(userInputBundle.contains(UserInputType.CONFIRM));
        assertTrue(userInputBundle.isFastForward());
    }

    @Test
    void testAddUserInput_AddsAfterConfirmRemoved() {
        // Arrange
        UserInputBundle userInputBundle = new UserInputBundle();
        userInputBundle.addUserInput(UserInputType.CONFIRM);
        userInputBundle.removeUserInput(UserInputType.CONFIRM);

        // Act
        userInputBundle.addUserInput(UserInputType.UP);

        // Assert
        assertEquals(1, userInputBundle.size());
        assertTrue(userInputBundle.contains(UserInputType.UP));
        assertFalse(userInputBundle.isFastForward());
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

    @Test
    void testRemoveUserInput_RemovesExistingInputFromList() {
        // Arrange
        UserInputBundle userInputBundle = new UserInputBundle();
        UserInputType inputType = UserInputType.UP;
        userInputBundle.addUserInput(inputType);

        // Act
        userInputBundle.removeUserInput(inputType);

        // Assert
        assertEquals(0, userInputBundle.size());
        assertFalse(userInputBundle.contains(inputType));
    }

    @Test
    void testRemoveUserInput_DoesNothingWhenInputDoesNotExist() {
        // Arrange
        UserInputBundle userInputBundle = new UserInputBundle();
        UserInputType inputType = UserInputType.UP;

        // Act
        userInputBundle.removeUserInput(inputType);

        // Assert
        assertEquals(0, userInputBundle.size());
        assertFalse(userInputBundle.contains(inputType));
    }

    @Test
    void testRemoveUserInput_ResetsFastForwardWhenRemovingConfirm() {
        // Arrange
        UserInputBundle userInputBundle = new UserInputBundle();
        UserInputType confirmType = UserInputType.CONFIRM;
        userInputBundle.addUserInput(confirmType);
        userInputBundle.setFastForward(true);

        // Act
        userInputBundle.removeUserInput(confirmType);

        // Assert
        assertFalse(userInputBundle.isFastForward());
        assertFalse(userInputBundle.contains(confirmType));
    }
}