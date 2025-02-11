package de.cargame.model.service;

import de.cargame.model.entity.player.Player;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;


class PlayerServiceTest {


    @InjectMocks
    PlayerService playerService = new PlayerService();

    @Test
    void testIsPlaying_KeyboardPlayerALiveAndPlaying() {
        // Arrange
        playerService.createPlayerKeyboard();
        Player keyboardPlayer = playerService.getKeyboardPlayer();


        keyboardPlayer.setLives(1);
        keyboardPlayer.setPlaying(true);

        // Act
        boolean result = playerService.isPlaying(keyboardPlayer.getId());

        // Assert
        assertTrue(result, "Expected keyboard player to be playing");
    }

    @Test
    void testIsPlaying_KeyboardPlayerAliveButNotPlaying() {
        // Arrange
        playerService.createPlayerKeyboard();
        Player keyboardPlayer = playerService.getKeyboardPlayer();


        keyboardPlayer.setLives(1);
        keyboardPlayer.setPlaying(false);

        // Act
        boolean result = playerService.isPlaying(keyboardPlayer.getId());

        // Assert
        assertFalse(result, "Expected keyboard player to be playing");
    }

    @Test
    void testIsPlaying_KeyboardPlayerNotAliveButPlaying() {
        // Arrange
        playerService.createPlayerKeyboard();
        Player keyboardPlayer = playerService.getKeyboardPlayer();


        keyboardPlayer.setLives(0);
        keyboardPlayer.setPlaying(true);

        // Act
        boolean result = playerService.isPlaying(keyboardPlayer.getId());

        // Assert
        assertFalse(result, "Expected keyboard player to be playing");
    }

    @Test
    void testIsPlaying_KeyboardPlayerNotAliveAndNotPlaying() {
        // Arrange
        playerService.createPlayerKeyboard();
        Player keyboardPlayer = playerService.getKeyboardPlayer();


        keyboardPlayer.setLives(0);
        keyboardPlayer.setPlaying(false);

        // Act
        boolean result = playerService.isPlaying(keyboardPlayer.getId());

        // Assert
        assertFalse(result, "Expected keyboard player to be playing");
    }


    @Test
    void testIsPlaying_GamepadPlayerALiveAndPlaying() {
        // Arrange
        playerService.createPlayerGamepad();
        Player keyboardPlayer = playerService.getKeyboardPlayer();

        // Set up player state directly to avoid spying
        keyboardPlayer.setLives(1);
        keyboardPlayer.setPlaying(true);

        // Act
        boolean result = playerService.isPlaying(keyboardPlayer.getId());

        // Assert
        assertTrue(result, "Expected keyboard player to be playing");
    }

    @Test
    void testIsPlaying_GamepadPlayerAliveButNotPlaying() {
        // Arrange
        playerService.createPlayerGamepad();
        Player keyboardPlayer = playerService.getKeyboardPlayer();


        keyboardPlayer.setLives(1);
        keyboardPlayer.setPlaying(false);

        // Act
        boolean result = playerService.isPlaying(keyboardPlayer.getId());

        // Assert
        assertFalse(result, "Expected keyboard player to be playing");
    }

    @Test
    void testIsPlaying_GamepadPlayerNotAliveButPlaying() {
        // Arrange
        playerService.createPlayerGamepad();
        Player keyboardPlayer = playerService.getKeyboardPlayer();

        // Set up player state directly to avoid spying
        keyboardPlayer.setLives(0);
        keyboardPlayer.setPlaying(true);

        // Act
        boolean result = playerService.isPlaying(keyboardPlayer.getId());

        // Assert
        assertFalse(result, "Expected keyboard player to be playing");
    }

    @Test
    void testIsPlaying_GamepadPlayerNotAliveAndNotPlaying() {
        // Arrange
        playerService.createPlayerGamepad();  // Ensure the keyboard player is created first
        Player keyboardPlayer = playerService.getKeyboardPlayer(); // Obtain the keyboard player

        // Set up player state directly to avoid spying
        keyboardPlayer.setLives(0);       // The player is alive
        keyboardPlayer.setPlaying(false); // The player is playing

        // Act
        boolean result = playerService.isPlaying(keyboardPlayer.getId());

        // Assert
        assertFalse(result, "Expected keyboard player to be playing");
    }


    @Test
    void testIsPlaying_NoPlayerWithGivenId() {
        // Arrange
        PlayerService playerService = new PlayerService();
        String invalidPlayerId = "invalid-id";

        // Act
        boolean result = playerService.isPlaying(invalidPlayerId);

        // Assert
        assertFalse(result, "Expected no player with the given ID to be playing");
    }

}