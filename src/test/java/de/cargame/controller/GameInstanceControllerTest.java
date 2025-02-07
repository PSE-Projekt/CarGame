package de.cargame.controller;

import de.cargame.controller.api.PlayerAPI;
import de.cargame.exception.PlayerNotFoundException;
import de.cargame.model.entity.player.Player;
import de.cargame.model.handler.GameStateHandler;
import de.cargame.model.service.GameInstanceService;
import de.cargame.model.service.PlayerService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GameInstanceControllerTest {


    GameApplicationManager gameApplicationManager = spy(new GameApplicationManager());

    PlayerAPI playerAPI = spy(new PlayerController(new PlayerService()));

    GameStateController gameStateController = spy(new GameStateController(new GameStateHandler()));

    GameInstanceService gameInstanceService = spy(new GameInstanceService(gameApplicationManager, gameStateController));

    @InjectMocks
    GameInstanceController gameInstanceController = new GameInstanceController(gameInstanceService, playerAPI);


    @Test
    void startGamePlayerKeyboardTest() {
        Player mockPlayer = spy(new Player());
        when(playerAPI.getKeyboardPlayer()).thenReturn(mockPlayer);
        gameInstanceController.startGamePlayerKeyboard();

        verify(playerAPI).getKeyboardPlayer();
        verify(mockPlayer).resetLives();
        verify(gameInstanceService).startGame(mockPlayer);
        verify(gameInstanceService).addGameInstance(any());
    }

    @Test
    void startGamePlayerGamePadTest() {
        Player mockPlayer = spy(new Player());
        when(playerAPI.getGamepadPlayer()).thenReturn(mockPlayer);
        gameInstanceController.startGamePlayerGamePad();

        verify(playerAPI).getGamepadPlayer();
        verify(mockPlayer).resetLives();
        verify(gameInstanceService).startGame(mockPlayer);
        verify(gameInstanceService).addGameInstance(any());
    }


    @Test
    void startGamePlayerKeyboardThrowsExceptionWhenPlayerNotFoundTest() {
        when(playerAPI.getKeyboardPlayer()).thenThrow(new PlayerNotFoundException("Player not present"));

        assertThrows(PlayerNotFoundException.class, () -> gameInstanceController.startGamePlayerKeyboard());
        verify(playerAPI).getKeyboardPlayer();
    }

    @Test
    void startGamePlayerGamePadThrowsExceptionWhenPlayerNotFoundTest() {
        when(playerAPI.getGamepadPlayer()).thenThrow(new PlayerNotFoundException("Player not present"));

        assertThrows(PlayerNotFoundException.class, () -> gameInstanceController.startGamePlayerGamePad());
        verify(playerAPI).getGamepadPlayer();
    }
}
