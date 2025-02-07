package de.cargame.controller;

import de.cargame.controller.entity.GameMode;
import de.cargame.controller.entity.GameState;
import de.cargame.model.handler.GameStateHandler;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)

public class GameStateControllerTest {


    GameStateHandler gameStateHandler = spy(new GameStateHandler());

    GameStateController gameStateController = new GameStateController(gameStateHandler);


    @Test
    void testGetGameMode() {
        // Arrange
        GameMode expectedGameMode = GameMode.SINGLEPLAYER;
        gameStateHandler.setGameMode(expectedGameMode);

        // Act
        GameMode result = gameStateController.getGameMode();

        // Assert
        assertEquals(expectedGameMode, result);
    }

    @Test
    void testSetGameMode() {
        // Arrange
        GameMode newGameMode = GameMode.MULTIPLAYER;

        // Act
        gameStateController.setGameMode(newGameMode);

        // Assert
        verify(gameStateHandler).setGameMode(newGameMode);
        assertEquals(newGameMode, gameStateHandler.getGameMode());
    }

    @Test
    void testGetGameState() {
        // Arrange
        GameState expectedGameState = GameState.IN_GAME;
        gameStateHandler.setGameState(expectedGameState);

        // Act
        GameState result = gameStateController.getGameState();

        // Assert
        assertEquals(expectedGameState, result);
    }

    @Test
    void testSetGameState() {
        // Arrange
        GameState newGameState = GameState.CAR_SELECTION;

        // Act
        gameStateController.setGameState(newGameState);

        // Assert
        verify(gameStateHandler).setGameState(newGameState);
        assertEquals(newGameState, gameStateHandler.getGameState());
    }

}
