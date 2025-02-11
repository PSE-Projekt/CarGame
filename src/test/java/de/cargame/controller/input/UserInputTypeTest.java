package de.cargame.controller.input;

import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class UserInputTypeTest {

    /**
     * Class UserInputType provides an enum representation of user input keys,
     * each associated with a specific keycode. The method getUserInputForKeyCode
     * is responsible for returning an Optional containing the corresponding
     * UserInputType for a given keyCode, or an empty Optional if the keyCode
     * does not match any defined UserInputType.
     */

    @Test
    void testGetUserInputForKeyCodeWhenKeyCodeMatchesUP() {
        // Given
        int keyCode = 119;

        // When
        Optional<UserInputType> result = UserInputType.getUserInputForKeyCode(keyCode);

        // Then
        assertTrue(result.isPresent());
        assertEquals(UserInputType.UP, result.get());
    }

    @Test
    void testGetUserInputForKeyCodeWhenKeyCodeMatchesLEFT() {
        // Given
        int keyCode = 97;

        // When
        Optional<UserInputType> result = UserInputType.getUserInputForKeyCode(keyCode);

        // Then
        assertTrue(result.isPresent());
        assertEquals(UserInputType.LEFT, result.get());
    }

    @Test
    void testGetUserInputForKeyCodeWhenKeyCodeMatchesDOWN() {
        // Given
        int keyCode = 115;

        // When
        Optional<UserInputType> result = UserInputType.getUserInputForKeyCode(keyCode);

        // Then
        assertTrue(result.isPresent());
        assertEquals(UserInputType.DOWN, result.get());
    }

    @Test
    void testGetUserInputForKeyCodeWhenKeyCodeMatchesRIGHT() {
        // Given
        int keyCode = 100;

        // When
        Optional<UserInputType> result = UserInputType.getUserInputForKeyCode(keyCode);

        // Then
        assertTrue(result.isPresent());
        assertEquals(UserInputType.RIGHT, result.get());
    }

    @Test
    void testGetUserInputForKeyCodeWhenKeyCodeMatchesCONFIRM() {
        // Given
        int keyCode = 114;

        // When
        Optional<UserInputType> result = UserInputType.getUserInputForKeyCode(keyCode);

        // Then
        assertTrue(result.isPresent());
        assertEquals(UserInputType.CONFIRM, result.get());
    }

    @Test
    void testGetUserInputForKeyCodeWhenKeyCodeMatchesNONE() {
        // Given
        int keyCode = -1;

        // When
        Optional<UserInputType> result = UserInputType.getUserInputForKeyCode(keyCode);

        // Then
        assertTrue(result.isPresent());
        assertEquals(UserInputType.NONE, result.get());
    }

    @Test
    void testGetUserInputForKeyCodeWhenKeyCodeDoesNotMatchAny() {
        // Given
        int keyCode = 999;

        // When
        Optional<UserInputType> result = UserInputType.getUserInputForKeyCode(keyCode);

        // Then
        assertTrue(result.isEmpty());
    }
}