package de.cargame.model.handler;

import de.cargame.controller.input.UserInput;
import de.cargame.model.entity.gameobject.car.player.PlayerCar;
import de.cargame.model.entity.player.Player;
import de.cargame.model.entity.player.Score;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PlayerHandlerTest {

    @Test
    void testIncreaseScoreShouldIncreasePlayerScore() {
        // Arrange
        Player playerMock = Mockito.mock(Player.class);
        PlayerHandler playerHandler = new PlayerHandler(playerMock);
        double scoreToAdd = 10.5;

        // Act
        playerHandler.increaseScore(scoreToAdd);

        // Assert
        verify(playerMock, times(1)).increaseScore(scoreToAdd);
    }

    @Test
    void testResetScoreShouldCallResetScoreOnPlayer() {
        // Arrange
        Player playerMock = Mockito.mock(Player.class);
        PlayerHandler playerHandler = new PlayerHandler(playerMock);

        // Act
        playerHandler.resetScore();

        // Assert
        verify(playerMock, times(1)).resetScore();
    }

    @Test
    void testResetScoreShouldResetPlayerScoreToZero() {
        // Arrange
        Player playerMock = Mockito.mock(Player.class);
        Score scoreMock = Mockito.mock(Score.class);
        when(playerMock.getScore()).thenReturn(scoreMock);
        when(scoreMock.getValue()).thenReturn(0.0);
        PlayerHandler playerHandler = new PlayerHandler(playerMock);

        // Act
        playerHandler.resetScore();

        // Assert
        assertEquals(0.0, playerHandler.getScore());
        verify(playerMock, times(1)).resetScore();
    }

    @Test
    void testIsPlayerAliveReturnsFalseWhenPlayerIsNotAlive() {
        // Arrange
        Player playerMock = spy(Player.class);
        playerMock.setLives(0);
        playerMock.setPlaying(true);
        PlayerHandler playerHandler = new PlayerHandler(playerMock);

        // Act
        boolean result = playerHandler.isPlayerAlive();

        // Assert
        assertFalse(result);
        verify(playerMock, times(1)).isAlive();
    }

    @Test
    void testIsPlayerAliveReturnsTrueWhenPlayerIsAlive() {
        // Arrange
        Player playerMock = spy(Player.class);
        playerMock.setLives(1);
        playerMock.setPlaying(true);
        PlayerHandler playerHandler = new PlayerHandler(playerMock);

        // Act
        boolean result = playerHandler.isPlayerAlive();

        // Assert
        assertTrue(result);
        verify(playerMock, times(1)).isAlive();
    }

    @Test
    void testSetPlayerCarShouldSetPlayerCar() {
        // Arrange
        Player playerMock = Mockito.mock(Player.class);
        PlayerCar playerCarMock = Mockito.mock(PlayerCar.class);
        PlayerHandler playerHandler = new PlayerHandler(playerMock);

        // Act
        playerHandler.setPlayerCar(playerCarMock);

        // Assert
        verify(playerMock, times(1)).setPlayerCar(playerCarMock);
    }

    @Test
    void testGetLifeCountReturnsCorrectValue() {
        // Arrange
        Player playerMock = Mockito.mock(Player.class);
        PlayerHandler playerHandler = new PlayerHandler(playerMock);

        when(playerMock.getLives()).thenReturn(5);

        // Act
        int lifeCount = playerHandler.getLifeCount();

        // Assert
        assertEquals(5, lifeCount);
        verify(playerMock, times(1)).getLives();
    }

    @Test
    void testGetLifeCountWithZeroLives() {
        // Arrange
        Player playerMock = Mockito.mock(Player.class);
        PlayerHandler playerHandler = new PlayerHandler(playerMock);

        when(playerMock.getLives()).thenReturn(0);

        // Act
        int lifeCount = playerHandler.getLifeCount();

        // Assert
        assertEquals(0, lifeCount);
        verify(playerMock, times(1)).getLives();
    }

    @Test
    void testGetCurrentUserInputWhenInputIsPresent() {
        // Arrange
        Player playerMock = Mockito.mock(Player.class);
        UserInput userInputMock = Mockito.mock(UserInput.class);
        PlayerHandler playerHandler = new PlayerHandler(playerMock);

        when(playerMock.getUserInput()).thenReturn(Optional.of(userInputMock));

        // Act
        Optional<UserInput> result = playerHandler.getCurrentUserInput();

        // Assert
        assertTrue(result.isPresent());
        assertEquals(userInputMock, result.get());
        verify(playerMock, times(1)).getUserInput();
    }

    @Test
    void testGetCurrentUserInputWhenInputIsEmpty() {
        // Arrange
        Player playerMock = Mockito.mock(Player.class);
        PlayerHandler playerHandler = new PlayerHandler(playerMock);

        when(playerMock.getUserInput()).thenReturn(Optional.empty());

        // Act
        Optional<UserInput> result = playerHandler.getCurrentUserInput();

        // Assert
        assertTrue(result.isEmpty());
        verify(playerMock, times(1)).getUserInput();
    }
}