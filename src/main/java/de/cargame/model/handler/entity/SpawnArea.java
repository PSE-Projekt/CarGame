package de.cargame.model.handler.entity;

import de.cargame.model.entity.gameobject.Coordinate;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

@Getter
@AllArgsConstructor
public class SpawnArea {
    private Coordinate minCoordinates;
    private Coordinate maxCoordinates;

    private Random r = new Random();


    public SpawnArea(int minX, int minY, int maxX, int maxY) {
        minCoordinates = new Coordinate(minX, minY);
        maxCoordinates = new Coordinate(maxX, maxY);
    }


    public Coordinate getRandomCoordinateInArea() {
        double randomX = ThreadLocalRandom.current().nextDouble(minCoordinates.getX(), maxCoordinates.getX() + 1);
        double randomY = ThreadLocalRandom.current().nextDouble(minCoordinates.getY(), maxCoordinates.getY() + 1);

        return new Coordinate(randomX, randomY);
    }
}
