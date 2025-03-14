package de.cargame.model.handler;

import de.cargame.config.GameConfigService;
import de.cargame.controller.api.GameStateAPI;
import de.cargame.controller.entity.GameMode;
import de.cargame.model.service.GameObjectService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import static org.mockito.Mockito.*;

class GameObjectSpawnSchedulerTest {

    @AfterEach
    void resetSingleton() throws NoSuchFieldException, IllegalAccessException {
        Field instance = GameConfigService.class.getDeclaredField("INSTANCE");
        instance.setAccessible(true);
        instance.set(null, null); // Setze das Singleton auf null zurück (ursprünglicher Zustand)
    }


    @Test
    void startSpawning_shouldScheduleAllSpawns() {
        // Mock dependencies
        GameObjectService gameObjectService = mock(GameObjectService.class);
        PlayerHandler playerHandler = mock(PlayerHandler.class);
        GameStateAPI gameStateAPI = mock(GameStateAPI.class);
        GameConfigService configService = mock(GameConfigService.class);

        // Define behavior for mocks
        when(gameStateAPI.getGameMode()).thenReturn(GameMode.SINGLEPLAYER);
        when(configService.loadInteger(any())).thenReturn(100); // Generic values for all configurations

        // Create the scheduler
        GameObjectSpawnScheduler scheduler = new GameObjectSpawnScheduler(playerHandler, gameObjectService, gameStateAPI);

        // Spy on the internal scheduler to verify scheduling methods
        ScheduledExecutorService schedulerSpy = spy(scheduler.scheduler);
        scheduler.scheduler = schedulerSpy;

        // Call startSpawning
        scheduler.startSpawning();

        // Capture internal scheduling
        verify(schedulerSpy, times(5)).schedule(any(Runnable.class), anyLong(), any(TimeUnit.class));
        scheduler.stopSpawning();
    }

    @Test
    void startSpawning_inMultiplayerMode_shouldScheduleAllSpawns() throws NoSuchFieldException, IllegalAccessException {
        // Mock dependencies
        GameObjectService gameObjectService = mock(GameObjectService.class);
        PlayerHandler playerHandler = mock(PlayerHandler.class);
        GameStateAPI gameStateAPI = mock(GameStateAPI.class);
        GameConfigService configServiceMock = mock(GameConfigService.class);

        // Define behavior for mocks
        when(gameStateAPI.getGameMode()).thenReturn(GameMode.MULTIPLAYER);
        when(configServiceMock.loadInteger(any())).thenReturn(200); // Generic values for all configurations

        GameConfigService configServiceOld = GameConfigService.getInstance();
        Field instance = GameConfigService.class.getDeclaredField("INSTANCE");
        instance.setAccessible(true);
        instance.set(configServiceOld, configServiceMock);

        // Create the scheduler
        GameObjectSpawnScheduler scheduler = new GameObjectSpawnScheduler(playerHandler, gameObjectService, gameStateAPI);

        // Spy on the internal scheduler
        ScheduledExecutorService schedulerSpy = spy(scheduler.scheduler);
        scheduler.scheduler = schedulerSpy;

        // Call startSpawning
        scheduler.startSpawning();

        // Capture internal scheduling
        verify(schedulerSpy, times(5)).schedule(any(Runnable.class), anyLong(), any(TimeUnit.class));

    }


}