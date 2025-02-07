package de.cargame.model.handler.entity;

import de.cargame.model.entity.gameobject.Coordinate;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Represents a rectangular spawn area defined by minimum and maximum coordinates.
 * This class provides functionality for generating random coordinates within
 * the boundaries of the defined spawn area.
 * <p>
 * Each SpawnArea is represented by:
 * - Minimum coordinates (lower-left corner of the rectangle)
 * - Maximum coordinates (upper-right corner of the rectangle)
 * <p>
 * Constructors allow for defining the spawn area either by directly passing
 * Coordinate objects or by specifying the x and y boundaries numerically.
 * <p>
 * Features:
 * - Generate a random coordinate within the spawn area.
 * - Retrieve string representation of the spawn area for debugging or logging purposes.
 */
@Getter
@Slf4j
@AllArgsConstructor
public class SpawnArea {
    private Coordinate minCoordinates;
    private Coordinate maxCoordinates;

    private Random r = new Random();


    public SpawnArea(int minX, int minY, int maxX, int maxY) {
        minCoordinates = new Coordinate(minX, minY);
        maxCoordinates = new Coordinate(maxX, maxY);
    }


    /**
     * Generates a random coordinate within the defined rectangular spawn area.
     * The random coordinate is constrained by the minimum and maximum x and y
     * boundaries of the spawn area.
     *
     * @return A random {@code Coordinate} within the spawn area's boundaries.
     */
    public Coordinate getRandomCoordinateInArea() {
        double randomX = ThreadLocalRandom.current().nextDouble(minCoordinates.getX(), maxCoordinates.getX() + 1);
        double randomY = ThreadLocalRandom.current().nextDouble(minCoordinates.getY(), maxCoordinates.getY() + 1);
        return new Coordinate(randomX, randomY);
    }

    @Override
    public String toString() {
        return "SpawnArea [minCoordinates=" + minCoordinates + ", maxCoordinates=" + maxCoordinates + "]";
    }
}
