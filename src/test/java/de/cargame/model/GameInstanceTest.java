package de.cargame.model;

import de.cargame.config.GameConfigService;
import de.cargame.controller.GameApplicationManager;
import de.cargame.controller.GameStateController;
import de.cargame.controller.api.GameStateAPI;
import de.cargame.controller.entity.GameMode;
import de.cargame.model.entity.player.Player;
import de.cargame.model.handler.GameStateHandler;
import de.cargame.model.handler.PlayerHandler;
import de.cargame.model.service.GameInstanceService;
import de.cargame.model.service.GameObjectService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

class GameInstanceTest {


    @AfterEach
    void resetSingleton() throws NoSuchFieldException, IllegalAccessException {
        Field instance = GameConfigService.class.getDeclaredField("INSTANCE");
        instance.setAccessible(true);
        instance.set(null, null); // Setze das Singleton auf null zurück (ursprünglicher Zustand)
    }

    @Test
    void testRunEndsWhenPlayerLosesLives_SINGLEPLAYER() throws NoSuchFieldException, IllegalAccessException {
        // Arrange
        Player mockPlayer = mock(Player.class);
        GameInstanceService mockService = mock(GameInstanceService.class);
        GameApplicationManager mockAppManager = mock(GameApplicationManager.class);
        GameStateHandler gameStateHandlerMock = mock(GameStateHandler.class);
        GameStateAPI mockGameStateAPI = spy(new GameStateController(gameStateHandlerMock));
        PlayerHandler mockPlayerHandler = mock(PlayerHandler.class);
        when(mockPlayerHandler.isPlayerAlive()).thenReturn(false);


        when(gameStateHandlerMock.getGameMode()).thenReturn(GameMode.SINGLEPLAYER);
        GameObjectService mockGameObjectService = spy(new GameObjectService(mockGameStateAPI, mockPlayerHandler));

        GameInstance gameInstance = new GameInstance(mockService, mockGameStateAPI, mockAppManager, mockPlayer);
        Field gameObjectService = GameInstance.class.getDeclaredField("gameObjectService");
        gameObjectService.setAccessible(true);
        gameObjectService.set(gameInstance, mockGameObjectService);
        doNothing().when(mockGameObjectService).startGame();
        // Act
        gameInstance.run();

        // Assert
        assertTrue(gameInstance.isFinished());
    }


    @Test
    void testRunExitsImmediatelyForMultiplayerWhenAllPlayersAreDead() throws NoSuchFieldException, IllegalAccessException {
        // Arrange
        Player mockPlayer = mock(Player.class);
        GameInstanceService mockService = mock(GameInstanceService.class);
        GameApplicationManager mockAppManager = mock(GameApplicationManager.class);
        GameStateHandler gameStateHandlerMock = mock(GameStateHandler.class);
        GameStateAPI mockGameStateAPI = spy(new GameStateController(gameStateHandlerMock));
        PlayerHandler mockPlayerHandler = mock(PlayerHandler.class);
        when(mockPlayerHandler.isPlayerAlive()).thenReturn(false);

        when(gameStateHandlerMock.getGameMode()).thenReturn(GameMode.MULTIPLAYER);

        GameObjectService mockGameObjectService = spy(new GameObjectService(mockGameStateAPI, mockPlayerHandler));
        GameInstance gameInstance = new GameInstance(mockService, mockGameStateAPI, mockAppManager, mockPlayer);

        Field gameObjectService = GameInstance.class.getDeclaredField("gameObjectService");
        gameObjectService.setAccessible(true);
        gameObjectService.set(gameInstance, mockGameObjectService);

        doNothing().when(mockGameObjectService).startGame();

        // Act
        gameInstance.run();

        // Assert
        assertTrue(gameInstance.isFinished());
        verify(mockGameObjectService, times(0)).update(anyDouble());
    }

}