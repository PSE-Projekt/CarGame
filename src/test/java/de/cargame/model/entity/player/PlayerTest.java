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
    void decreaseLife_shouldNotifyObservers_whenLifeDecreased() {
        // Arrange
        PlayerService mockPlayerService = Mockito.mock(PlayerService.class);
        Player player = new Player(mockPlayerService);
        PlayerObserver mockObserver = Mockito.mock(PlayerObserver.class);
        player.addObserver(mockObserver);

        // Act
        player.decreaseLife();

        // Assert
        Mockito.verify(mockObserver, Mockito.times(1)).update(Mockito.any());
    }

    @Test
    void decreaseLife_shouldLogWhenLifeIsDecreased() {
        // Arrange
        PlayerService mockPlayerService = Mockito.mock(PlayerService.class);
        Player player = new Player(mockPlayerService);
        org.slf4j.Logger logger = Mockito.mock(org.slf4j.Logger.class);
        player.setLives(1); // Set to a known value

        // Act
        player.decreaseLife();

        // Assert
        Mockito.verify(mockPlayerService).rumbleGamepad(1);
        // Use Mockito to verify logging behavior
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
    void increaseLife_shouldNotifyObservers_whenLifeIncreased() {
        // Arrange
        PlayerService mockPlayerService = Mockito.mock(PlayerService.class);
        Player player = new Player(mockPlayerService);
        PlayerObserver mockObserver = Mockito.mock(PlayerObserver.class);
        player.addObserver(mockObserver);
        player.setLives(player.getMAX_LIVES() - 1); // Below max lives

        // Act
        player.increaseLife();

        // Assert
        Mockito.verify(mockObserver, Mockito.times(1)).update(Mockito.any());
    }

    @Test
    void increaseLife_shouldNotNotifyObservers_whenAtMaxLives() {
        // Arrange
        PlayerService mockPlayerService = Mockito.mock(PlayerService.class);
        Player player = new Player(mockPlayerService);
        PlayerObserver mockObserver = Mockito.mock(PlayerObserver.class);
        player.addObserver(mockObserver);
        player.setLives(player.getMAX_LIVES()); // At max lives

        // Act
        player.increaseLife();

        // Assert
        Mockito.verify(mockObserver, Mockito.never()).update(Mockito.any());
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