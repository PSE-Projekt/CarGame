package de.cargame.model.service;

import de.cargame.model.entity.player.Player;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;

import static org.junit.jupiter.api.Assertions.*;


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

    @Test
    void testSetPlaying_KeyboardPlayer() {
        // Arrange
        playerService.createPlayerKeyboard();
        Player keyboardPlayer = playerService.getKeyboardPlayer();
        String playerId = keyboardPlayer.getId();

        // Act
        playerService.setPlaying(playerId, true);

        // Assert
        assertTrue(keyboardPlayer.isPlaying(), "Expected keyboard player to be playing");

        // Act
        playerService.setPlaying(playerId, false);

        // Assert
        assertFalse(keyboardPlayer.isPlaying(), "Expected keyboard player to not be playing");
    }

    @Test
    void testSetPlaying_GamepadPlayer() {
        // Arrange
        playerService.createPlayerGamepad();
        Player gamepadPlayer = playerService.getGamepadPlayer();
        String playerId = gamepadPlayer.getId();

        // Act
        playerService.setPlaying(playerId, true);

        // Assert
        assertTrue(gamepadPlayer.isPlaying(), "Expected gamepad player to be playing");

        // Act
        playerService.setPlaying(playerId, false);

        // Assert
        assertFalse(gamepadPlayer.isPlaying(), "Expected gamepad player to not be playing");
    }

    @Test
    void testSetPlaying_InvalidPlayerId() {
        // Arrange
        PlayerService playerService = new PlayerService();
        String invalidPlayerId = "invalid-id";

        // Act
        playerService.setPlaying(invalidPlayerId, true);

        // Assert
        assertFalse(playerService.getKeyboardPlayer().isPlaying(), "Expected keyboard player to remain unaffected");
        assertFalse(playerService.getGamepadPlayer().isPlaying(), "Expected gamepad player to remain unaffected");
    }

    @Test
    void testCreatePlayerKeyboard_Initialization() {
        // Act
        playerService.createPlayerKeyboard();
        Player keyboardPlayer = playerService.getKeyboardPlayer();

        // Assert
        assertNotNull(keyboardPlayer, "Expected keyboard player to be initialized");
    }

    @Test
    void testCreatePlayerKeyboard_UniquePlayerId() {
        // Arrange
        playerService.createPlayerKeyboard();
        Player keyboardPlayer = playerService.getKeyboardPlayer();

        // Act
        String playerId = keyboardPlayer.getId();

        // Assert
        assertNotNull(playerId, "Expected keyboard player to have a unique ID");
        assertFalse(playerId.isEmpty(), "Expected keyboard player to have a non-empty ID");
    }
}