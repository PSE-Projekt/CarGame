package de.cargame.model.entity.gameobject.car.ai;

import de.cargame.controller.entity.GameMode;
import de.cargame.model.entity.gameobject.Coordinate;

/**
 * Represents a movement strategy for AI cars that move in a straight line within the game.
 * This class is a concrete implementation of {@link AICarMovementStrategy}.
 * <p>
 * The target position for the AI car is calculated as a constant x-coordinate value
 * while maintaining the initial y-coordinate from the spawn position. The x-coordinate
 * is set to a predefined value (-1000), ensuring that the car progresses in a linear
 * path horizontally across the screen.
 * <p>
 * This strategy is typically suitable for AI cars with non-deviating, predictable,
 * linear movement patterns such as those categorized as {@link AICarType#STRAIGHT_MOVING}.
 */
public class StraightMovementStrategy extends AICarMovementStrategy {

    public StraightMovementStrategy(Coordinate gameObjectSpawnCoordinate, GameMode gameMode) {
        super(gameObjectSpawnCoordinate, gameMode);
    }

    @Override
    public void calcTargetPos() {
        this.targetPos = new Coordinate(-1000, gameObjectSpawnCoordinate.getY());
    }
}
