package de.cargame.model.service;

import de.cargame.controller.GameApplicationManager;
import de.cargame.controller.api.GameStateAPI;
import de.cargame.controller.entity.GameModelData;
import de.cargame.controller.entity.GameState;
import de.cargame.model.GameInstance;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class GameInstanceServiceTest {

    /**
     * Tests for the GameInstanceService#getModel() method.
     * <p>
     * The getModel() method returns a list of GameModelData objects,
     * retrieved from each game instance currently managed by this service.
     */

    @Test
    void getModel_returnsEmptyList_whenNoGameInstancesExist() {
        // Arrange
        GameApplicationManager gameApplicationManager = mock(GameApplicationManager.class);
        GameStateAPI gameStateController = mock(GameStateAPI.class);
        GameInstanceService service = new GameInstanceService(gameApplicationManager, gameStateController);

        // Act
        List<GameModelData> result = service.getModel();

        // Assert
        assertEquals(0, result.size(), "Expected an empty list when no game instances exist.");
    }

    @Test
    void checkGameState_returnsTrue_whenAllGameInstancesAreFinished() {
        // Arrange
        GameApplicationManager gameApplicationManager = mock(GameApplicationManager.class);
        GameStateAPI gameStateController = mock(GameStateAPI.class);
        GameInstanceService service = new GameInstanceService(gameApplicationManager, gameStateController);

        GameInstance finishedGameInstance1 = mock(GameInstance.class);
        GameInstance finishedGameInstance2 = mock(GameInstance.class);

        when(finishedGameInstance1.isFinished()).thenReturn(true);
        when(finishedGameInstance2.isFinished()).thenReturn(true);

        service.addGameInstance(finishedGameInstance1);
        service.addGameInstance(finishedGameInstance2);

        // Act
        boolean result = service.checkGameState();

        // Assert
        assertEquals(true, result, "Expected checkGameState to return true when all game instances are finished.");
        verify(gameStateController).setGameState(GameState.SCORE_BOARD);
    }

    @Test
    void checkGameState_returnsFalse_whenSomeGameInstancesAreNotFinished() {
        // Arrange
        GameApplicationManager gameApplicationManager = mock(GameApplicationManager.class);
        GameStateAPI gameStateController = mock(GameStateAPI.class);
        GameInstanceService service = new GameInstanceService(gameApplicationManager, gameStateController);

        GameInstance finishedGameInstance = mock(GameInstance.class);
        GameInstance unfinishedGameInstance = mock(GameInstance.class);

        when(finishedGameInstance.isFinished()).thenReturn(true);
        when(unfinishedGameInstance.isFinished()).thenReturn(false);

        service.addGameInstance(finishedGameInstance);
        service.addGameInstance(unfinishedGameInstance);

        // Act
        boolean result = service.checkGameState();

        // Assert
        assertEquals(false, result, "Expected checkGameState to return false when some game instances are not finished.");
        verify(gameStateController, Mockito.never()).setGameState(GameState.SCORE_BOARD);
    }

    @Test
    void getModel_returnsListOfGameModelData_whenGameInstancesExist() {
        // Arrange
        GameApplicationManager gameApplicationManager = mock(GameApplicationManager.class);
        GameStateAPI gameStateController = mock(GameStateAPI.class);
        GameInstanceService service = new GameInstanceService(gameApplicationManager, gameStateController);

        GameInstance gameInstance1 = mock(GameInstance.class);
        GameInstance gameInstance2 = mock(GameInstance.class);

        GameModelData modelData1 = mock(GameModelData.class);
        GameModelData modelData2 = mock(GameModelData.class);

        when(gameInstance1.getGameModelData()).thenReturn(modelData1);
        when(gameInstance2.getGameModelData()).thenReturn(modelData2);

        service.addGameInstance(gameInstance1);
        service.addGameInstance(gameInstance2);

        // Act
        List<GameModelData> result = service.getModel();

        // Assert
        assertEquals(2, result.size(), "Expected the list size to match the number of game instances.");
        assertEquals(modelData1, result.get(0), "Expected the first item to match the first instance's data.");
        assertEquals(modelData2, result.get(1), "Expected the second item to match the second instance's data.");
    }

    @Test
    void getModel_returnsEmptyList_afterGameInstancesReset() {
        // Arrange
        GameApplicationManager gameApplicationManager = mock(GameApplicationManager.class);
        GameStateAPI gameStateController = mock(GameStateAPI.class);
        GameInstanceService service = new GameInstanceService(gameApplicationManager, gameStateController);

        GameInstance gameInstance = mock(GameInstance.class);
        GameModelData modelData = mock(GameModelData.class);
        when(gameInstance.getGameModelData()).thenReturn(modelData);

        service.addGameInstance(gameInstance);

        // Act
        service.resetGameInstances();
        List<GameModelData> result = service.getModel();

        // Assert
        assertEquals(0, result.size(), "Expected an empty list after resetting the game instances.");
    }

    @Test
    void resetGameInstances_clearsExistingInstances() {
        // Arrange
        GameApplicationManager gameApplicationManager = mock(GameApplicationManager.class);
        GameStateAPI gameStateController = mock(GameStateAPI.class);
        GameInstanceService service = new GameInstanceService(gameApplicationManager, gameStateController);

        GameInstance gameInstance1 = mock(GameInstance.class);
        GameInstance gameInstance2 = mock(GameInstance.class);

        service.addGameInstance(gameInstance1);
        service.addGameInstance(gameInstance2);

        // Act
        service.resetGameInstances();

        // Assert
        assertEquals(0, service.getModel().size(), "Expected game instances to be empty after reset.");
    }

    @Test
    void resetGameInstances_noEffect_whenNoInstancesExist() {
        // Arrange
        GameApplicationManager gameApplicationManager = mock(GameApplicationManager.class);
        GameStateAPI gameStateController = mock(GameStateAPI.class);
        GameInstanceService service = new GameInstanceService(gameApplicationManager, gameStateController);

        // Act
        service.resetGameInstances();

        // Assert
        assertEquals(0, service.getModel().size(), "Expected game instances to remain empty after reset with no instances.");
    }
}