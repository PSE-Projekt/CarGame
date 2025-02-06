package de.cargame.model.entity.gameobject.car.ai;

import de.cargame.controller.entity.GameMode;
import de.cargame.model.entity.gameobject.Coordinate;
import lombok.extern.slf4j.Slf4j;

import java.util.Random;

@Slf4j
public class CrossMovementStrategy extends AICarMovementStrategy {

    private final Random random = new Random();

    public CrossMovementStrategy(Coordinate gameObjectSpawnCoordinate, GameMode gameMode) {
        super(gameObjectSpawnCoordinate, gameMode);

    }

    @Override
    public void calcTargetPos() {

        double randomX = -500;

        int targetYPossibleHeight = gameMode == GameMode.SINGLEPLAYER ? SCREEN_HEIGHT : SCREEN_HEIGHT/2;

        double randomY = random.nextInt(targetYPossibleHeight);
        this.targetPos = new Coordinate(randomX, randomY);
    }

}
