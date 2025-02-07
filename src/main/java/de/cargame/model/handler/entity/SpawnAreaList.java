package de.cargame.model.handler.entity;

import de.cargame.model.entity.gameobject.Coordinate;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * A container for managing and interacting with a collection of {@code SpawnArea} objects.
 * This class provides functionality for adding, removing, and retrieving random spawn areas
 * and coordinates within those spawn areas. It is designed to assist in handling multiple
 * spawn areas in scenarios like spawning game objects in a two-dimensional space.
 *
 * Features:
 * - Ability to add individual {@code SpawnArea} objects.
 * - Ability to add multiple {@code SpawnArea} objects at once.
 * - Retrieve a random {@code SpawnArea} from the collection.
 * - Generate random coordinates from either all spawn areas or a single random spawn area.
 */
public class SpawnAreaList {
    private final List<SpawnArea> spawnAreas;
    private final Random random = new Random();

    /**
     * Constructs an empty list of {@code SpawnArea} objects.
     * This constructor initializes the internal collection used to store and
     * manage spawn areas, allowing the addition, removal, and manipulation of
     * spawn areas for various purposes in the application.
     */
    public SpawnAreaList() {
        this.spawnAreas = new ArrayList<>();
    }

    /**
     * Adds a {@code SpawnArea} to the collection of spawn areas.
     *
     * @param spawnArea the {@code SpawnArea} to be added to the list
     */
    public void add(SpawnArea spawnArea) {
        spawnAreas.add(spawnArea);
    }

    /**
     * Adds all the {@code SpawnArea} objects from the provided list to the internal collection of spawn areas.
     *
     * @param spawnAreas the list of {@code SpawnArea} objects to be added to the collection
     */
    public void addAll(List<SpawnArea> spawnAreas) {
        this.spawnAreas.addAll(spawnAreas);
    }

    /**
     * Removes the specified {@code SpawnArea} from the collection of spawn areas.
     *
     * @param spawnArea the {@code SpawnArea} to be removed from the collection
     */
    public void remove(SpawnArea spawnArea) {
        spawnAreas.remove(spawnArea);
    }

    /**
     * Retrieves a random {@code SpawnArea} from the list of available spawn areas.
     *
     * @return A randomly chosen {@code SpawnArea} from the collection.
     */
    public SpawnArea getRandomSpawnArea() {
        return spawnAreas.get(random.nextInt(spawnAreas.size()));
    }


    /**
     * Generates one random coordinate for each SpawnArea present.
     *
     * @return List of random coordinates from each SpawnArea present.
     */
    public List<Coordinate> getRandomCoordinateOfEach() {
        List<Coordinate> coordinates = new ArrayList<>();
        for (SpawnArea spawnArea : spawnAreas) {
            coordinates.add(spawnArea.getRandomCoordinateInArea());
        }
        return coordinates;
    }


    /**
     * Generates one random coordinate from one random SpawnArea present.
     *
     * @return One random coordinate from one random SpawnArea which is present.
     */
    public Coordinate getRandomCoordinate() {
        SpawnArea spawnArea = spawnAreas.get(random.nextInt(spawnAreas.size()));
        return spawnArea.getRandomCoordinateInArea();
    }

    @Override
    public String toString() {
        return "SpawnAreaList [spawnAreas=" + spawnAreas + "]";
    }
}
