package de.cargame.model.entity.gameobject.car.ai;

import de.cargame.controller.entity.GameMode;
import de.cargame.model.entity.gameobject.Coordinate;

import java.util.Random;

public class CrossMovementStrategy extends AICarMovementStrategy {

    public CrossMovementStrategy(Coordinate gameObjectSpawnCoordinate, GameMode gameMode) {
        super(gameObjectSpawnCoordinate, gameMode);

    }

    @Override
    public void calcTargetPos() {

        Random random = new Random();
        double randomX = -500;

        int targetYPossibleHeight = gameMode == GameMode.SINGLEPLAYER ? SCREEN_HEIGHT : SCREEN_HEIGHT/2;

        double randomY = random.nextInt(targetYPossibleHeight);
        this.targetPos = new Coordinate(randomX, randomY);
    }

}
