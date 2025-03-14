package de.cargame.model.entity.player;

import de.cargame.model.service.PlayerService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PlayerTest {

    /**
     * Test class for the Player class.
     * Focused on testing the behavior of the decreaseLife method,
     * which is responsible for reducing the number of lives a player has, notifying observers,
     * and utilizing the PlayerService to rumble the gamepad.
     */

    @Test
    void decreaseLife_shouldDecreaseLivesByOne_whenCalled() {
        // Arrange
        PlayerService mockPlayerService = Mockito.mock(PlayerService.class);
        Player player = new Player(mockPlayerService);
        int initialLives = player.getLives();

        // Act
        player.decreaseLife();

        // Assert
        assertEquals(initialLives - 1, player.getLives(), "Expected the number of lives to decrease by 1.");
        Mockito.verify(mockPlayerService).rumbleGamepad(1);
    }

    @Test
    void decreaseLife_shouldAllowNegativeLives_whenCalledMultipleTimes() {
        // Arrange
        PlayerService mockPlayerService = Mockito.mock(PlayerService.class);
        Player player = new Player(mockPlayerService);
        int initialLives = player.getLives();

        // Act
        player.decreaseLife();
        player.decreaseLife();
        player.decreaseLife();
        player.decreaseLife(); // Decreasing below zero intentionally for test

        // Assert
        assertEquals(initialLives - 4, player.getLives(), "Expected lives to decrease correctly into negative.");
        Mockito.verify(mockPlayerService, Mockito.times(4)).rumbleGamepad(1);
    }

    @Test
    void increaseLife_shouldIncreaseLivesByOne_whenBelowMaxLives() {
        // Arrange
        PlayerService mockPlayerService = Mockito.mock(PlayerService.class);
        Player player = new Player(mockPlayerService);
        player.setLives(player.getMAX_LIVES() - 1); // Set lives to one less than MAX_LIVES
        int initialLives = player.getLives();

        // Act
        player.increaseLife();

        // Assert
        assertEquals(initialLives + 1, player.getLives(), "Expected the number of lives to increase by 1.");
    }

    @Test
    void increaseLife_shouldNotIncreaseLivesBeyondMax_whenAtMaxLives() {
        // Arrange
        PlayerService mockPlayerService = Mockito.mock(PlayerService.class);
        Player player = new Player(mockPlayerService);
        player.setLives(player.getMAX_LIVES()); // Set lives to maximum
        int initialLives = player.getLives();

        // Act
        player.increaseLife();

        // Assert
        assertEquals(initialLives, player.getLives(), "Lives should not exceed MAX_LIVES.");
    }
}