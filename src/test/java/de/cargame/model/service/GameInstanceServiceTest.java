package de.cargame.model.service;

import de.cargame.controller.GameApplicationManager;
import de.cargame.controller.api.GameStateAPI;
import de.cargame.controller.entity.GameModelData;
import de.cargame.model.GameInstance;
import de.cargame.model.entity.player.Player;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

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

}