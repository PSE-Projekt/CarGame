package de.cargame.model.service;

import de.cargame.controller.entity.GameMode;
import de.cargame.exception.InvalidCarSelectionException;
import de.cargame.model.entity.gameobject.Building;
import de.cargame.model.entity.gameobject.Coordinate;
import de.cargame.model.entity.gameobject.car.player.AgileCar;
import de.cargame.model.entity.gameobject.car.player.CarType;
import de.cargame.model.entity.gameobject.car.player.FastCar;
import de.cargame.model.entity.gameobject.car.player.PlayerCar;
import de.cargame.model.handler.entity.GameObjectSpawningStrategy;
import de.cargame.model.handler.entity.SpawnArea;
import de.cargame.model.handler.entity.SpawnAreaList;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class GameObjectCreationServiceTest {

    /**
     * This class is responsible for testing the `createPlayerCar` method in the `GameObjectCreationService` class.
     * The method creates a PlayerCar object of specific type (FAST_CAR or AGILE_CAR) based on the input car type.
     * It also ensures cars are spawned in valid spawn areas with appropriate dimensions.
     */

    @Test
    void testCreateBuildings_createsListOfBuildings () {
        GameObjectCreationService service = new GameObjectCreationService();
        GameObjectSpawningStrategy mockStrategy = Mockito.mock(GameObjectSpawningStrategy.class);
        SpawnAreaList mockSpawnAreas = Mockito.mock(SpawnAreaList.class);

        when(mockStrategy.getBuildingSpawnAreas()).thenReturn(mockSpawnAreas);
        when(mockSpawnAreas.getRandomCoordinateOfEach()).thenReturn(List.of(
                new Coordinate(10, 20),
                new Coordinate(30, 40)
        ));
        service.setGameMode(GameMode.SINGLEPLAYER);
        service.setGameObjectSpawningStrategy(mockStrategy);
        service.init();

        List<Building> buildings = service.createBuildings();

        assertEquals(2, buildings.size());
        assertEquals(10, buildings.get(0).getCoordinates().getX());
        assertEquals(20, buildings.get(0).getCoordinates().getY());
        assertEquals(30, buildings.get(1).getCoordinates().getX());
        assertEquals(40, buildings.get(1).getCoordinates().getY());
    }

    @Test
    void testCreateBuildings_noSpawnAreas_returnsEmptyList () {
        GameObjectCreationService service = new GameObjectCreationService();
        GameObjectSpawningStrategy mockStrategy = Mockito.mock(GameObjectSpawningStrategy.class);
        SpawnAreaList mockSpawnAreas = Mockito.mock(SpawnAreaList.class);

        when(mockStrategy.getBuildingSpawnAreas()).thenReturn(mockSpawnAreas);
        when(mockSpawnAreas.getRandomCoordinateOfEach()).thenReturn(List.of());

        service.setGameMode(GameMode.SINGLEPLAYER);
        service.setGameObjectSpawningStrategy(mockStrategy);
        service.init();

        List<Building> buildings = service.createBuildings();

        assertTrue(buildings.isEmpty());
    }

    @Test
    void testCreatePlayerCar_withFastCar_createsFastCar() {


        GameObjectCreationService service = new GameObjectCreationService();
        GameObjectSpawningStrategy mockStrategy = Mockito.mock(GameObjectSpawningStrategy.class);
        SpawnAreaList mockSpawnAreas = Mockito.mock(SpawnAreaList.class);
        SpawnArea mockSpawnArea = Mockito.mock(SpawnArea.class);

        when(mockStrategy.getPlayerSpawnAreas()).thenReturn(mockSpawnAreas);
        when(mockSpawnAreas.getRandomSpawnArea()).thenReturn(mockSpawnArea);
        when(mockSpawnArea.getRandomCoordinateInArea()).thenReturn(new Coordinate(50, 100));

        service.setGameMode(GameMode.SINGLEPLAYER);
        service.setGameObjectSpawningStrategy(mockStrategy);
        service.init();

        PlayerCar result = service.createPlayerCar(CarType.FAST_CAR);

        assertTrue(result instanceof FastCar);
        assertEquals(50, result.getCoordinates().getX());
        assertEquals(100, result.getCoordinates().getY());
        verify(mockSpawnAreas, times(1)).remove(mockSpawnArea);
    }

    @Test
    void testCreatePlayerCar_withAgileCar_createsAgileCar() {
        GameObjectCreationService service = new GameObjectCreationService();
        GameObjectSpawningStrategy mockStrategy = Mockito.mock(GameObjectSpawningStrategy.class);
        SpawnAreaList mockSpawnAreas = Mockito.mock(SpawnAreaList.class);
        SpawnArea mockSpawnArea = Mockito.mock(SpawnArea.class);

        when(mockStrategy.getPlayerSpawnAreas()).thenReturn(mockSpawnAreas);
        when(mockSpawnAreas.getRandomSpawnArea()).thenReturn(mockSpawnArea);
        when(mockSpawnArea.getRandomCoordinateInArea()).thenReturn(new Coordinate(150, 300));

        service.setGameMode(GameMode.MULTIPLAYER);
        service.setGameObjectSpawningStrategy(mockStrategy);
        service.init();

        PlayerCar result = service.createPlayerCar(CarType.AGILE_CAR);

        assertTrue(result instanceof AgileCar);
        assertEquals(150, result.getCoordinates().getX());
        assertEquals(300, result.getCoordinates().getY());
        verify(mockSpawnAreas, times(1)).remove(mockSpawnArea);
    }

    @Test
    void testCreatePlayerCar_withInvalidCarType_throwsInvalidCarSelectionException() {
        GameObjectCreationService service = new GameObjectCreationService();
        GameObjectSpawningStrategy mockStrategy = Mockito.mock(GameObjectSpawningStrategy.class);
        SpawnAreaList mockSpawnAreas = Mockito.mock(SpawnAreaList.class);

        when(mockStrategy.getPlayerSpawnAreas()).thenReturn(mockSpawnAreas);

        service.setGameMode(GameMode.MULTIPLAYER);
        service.setGameObjectSpawningStrategy(mockStrategy);
        service.init();

        assertThrows(InvalidCarSelectionException.class, () -> service.createPlayerCar(null));
    }
}