package de.cargame.model.entity.gameobject.car.ai;

import de.cargame.model.entity.gameobject.Coordinate;

import java.util.Random;

public class CrossMovementStrategy extends AICarMovementStrategy {

    public CrossMovementStrategy(Coordinate gameObjectSpawnCoordinate) {
        super(gameObjectSpawnCoordinate);

    }

    @Override
    public void calcTargetPos() {

        Random random = new Random();
        double randomX = -500;

        double randomY = random.nextInt(SCREEN_HEIGHT);
        this.targetPos = new Coordinate(randomX, randomY);
    }

}
