package de.cargame.model.handler.entity;

import de.cargame.model.entity.gameobject.Coordinate;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.spy;

class SpawnAreaListTest {


    private SpawnAreaList spawnAreaList = new SpawnAreaList();

    int coordinateMinX1 = 100;
    int coordinateMaxX1 = 200;
    int coordinateMinY1 = 300;
    int coordinateMaxY1 = 400;

    int coordinateMinX2 = 500;
    int coordinateMaxX2 = 600;
    int coordinateMinY2 = 700;
    int coordinateMaxY2 = 800;


    SpawnArea spawnArea1 = spy(new SpawnArea(coordinateMinX1, coordinateMinY1, coordinateMaxX1, coordinateMaxY1));
    SpawnArea spawnArea2 = spy(new SpawnArea(coordinateMinX2, coordinateMinY2, coordinateMaxX2, coordinateMaxY2));



    @Test
    void testGetRandomCoordinateOfEach_ReturnsCoordinatesForAllSpawnAreas() {
        // Arrange

        spawnAreaList.add(spawnArea1);
        spawnAreaList.add(spawnArea2);

        List<Coordinate> coordinateList = spawnAreaList.getRandomCoordinateOfEach();

        assertNotNull(coordinateList);
        assertEquals(2, coordinateList.size());

    }

    @Test
    void testGetRandomCoordinateOfEach_ReturnsEmptyListWhenNoSpawnAreas() {
        // Arrange
        SpawnAreaList emptySpawnAreaList = new SpawnAreaList();

        // Act
        List<Coordinate> coordinates = emptySpawnAreaList.getRandomCoordinateOfEach();

        // Assert
        assertNotNull(coordinates);
        assertTrue(coordinates.isEmpty());
    }

}