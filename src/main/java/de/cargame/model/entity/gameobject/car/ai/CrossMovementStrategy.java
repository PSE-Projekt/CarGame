package de.cargame.model.entity.gameobject.car.ai;

import de.cargame.controller.entity.GameMode;
import de.cargame.model.entity.gameobject.Coordinate;
import lombok.extern.slf4j.Slf4j;

/**
 * A strategy implementation for AI car movement that involves crossing paths dynamically.
 * The CrossMovementStrategy determines the target position for the AI car by selecting a
 * random y-coordinate within the bounds of the screen height. This movement strategy is designed to emulate cars crossing lanes
 * or areas in the game environment.
 * <p>
 * This strategy adapts to the current {@link GameMode} by calculating the screen height limits
 * differently for SINGLEPLAYER and MULTIPLAYER modes. Such behavior allows the strategy to
 * function appropriately under varying gameplay scenarios.
 * <p>
 * It extends the {@link AICarMovementStrategy} and implements the calculation logic
 * for setting the target position as part of the AI car's movement.
 */
@Slf4j
public class CrossMovementStrategy extends AICarMovementStrategy {


    public CrossMovementStrategy(Coordinate gameObjectSpawnCoordinate, GameMode gameMode) {
        super(gameObjectSpawnCoordinate, gameMode);
    }

    @Override
    public void calcTargetPos() {

        double randomX = -SCREEN_WIDTH;

        int targetYPossibleHeight = gameMode == GameMode.SINGLEPLAYER ? SCREEN_HEIGHT : SCREEN_HEIGHT / 2;

        double randomY = random.nextInt(targetYPossibleHeight);
        this.targetPos = new Coordinate(randomX, randomY);
    }

}
