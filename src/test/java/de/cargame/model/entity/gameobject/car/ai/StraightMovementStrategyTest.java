package de.cargame.model.entity.gameobject.car.ai;

import de.cargame.controller.entity.GameMode;
import de.cargame.model.entity.gameobject.Coordinate;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class StraightMovementStrategyTest {

    /**
     * Unit tests for the `calcTargetPos` method in the `StraightMovementStrategy` class.
     * <p>
     * The `calcTargetPos` method calculates and sets the target position of the AI car to a fixed
     * X-coordinate value of -1000 while maintaining the initial Y-coordinate.
     */

    @Test
    void testCalcTargetPos_MaintainsInitialYCoordinate() {
        // Arrange
        Coordinate spawnCoordinate = new Coordinate(0, 200);
        GameMode mockGameMode = GameMode.SINGLEPLAYER; // Replace with actual mock or constant if required
        StraightMovementStrategy strategy = new StraightMovementStrategy(spawnCoordinate, mockGameMode);

        // Act
        strategy.calcTargetPos();

        // Assert
        Coordinate targetPos = strategy.targetPos;
        assertEquals(-1000, targetPos.getX());
        assertEquals(spawnCoordinate.getY(), targetPos.getY());
    }

}