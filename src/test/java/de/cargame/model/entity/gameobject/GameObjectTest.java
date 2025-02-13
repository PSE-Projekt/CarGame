package de.cargame.model.entity.gameobject;

import de.cargame.controller.entity.GameMode;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

class GameObjectTest {

    private static List<GameObject> getStaticGameObjects() {
        List<GameObject> objects = new ArrayList<>();
        Coordinate realCoordinate = new Coordinate(1000, 100);
        Dimension mockDimension = mock(Dimension.class); // Dimension bleibt gemockt, weil sie hier nicht direkt getestet wird
        GameMode mockGameMode = mock(GameMode.class);

        objects.add(new Building(realCoordinate, mockDimension, GameObjectBoundType.RECTANGLE, mockGameMode));
        objects.add(new Obstacle(realCoordinate, mockDimension, GameObjectBoundType.RECTANGLE, mockGameMode));
        objects.add(new Life(realCoordinate, mockDimension, GameObjectBoundType.RECTANGLE, mockGameMode));
        objects.add(new RoadMark(realCoordinate, mockDimension, GameObjectBoundType.RECTANGLE, mockGameMode));


        return objects;
    }


    @ParameterizedTest
    @MethodSource("getStaticGameObjects")
    void testMoveWithFastForwardingEnabled(GameObject gameObject) {

        double deltaTime = 0.1;
        boolean isFastForwarding = true;

        // Act
        gameObject.move(deltaTime, isFastForwarding);

        // Assert
        assertEquals(952.0, gameObject.getX());
        assertEquals(100, gameObject.getY());
    }

    @ParameterizedTest
    @MethodSource("getStaticGameObjects")
    void testMoveWithFastForwardingDisabled(GameObject gameObject) {

        double deltaTime = 0.1;
        boolean isFastForwarding = false;

        // Act
        gameObject.move(deltaTime, isFastForwarding);

        // Assert
        assertEquals(965.0, gameObject.getX());
        assertEquals(100, gameObject.getY());
    }

    @ParameterizedTest
    @MethodSource("getStaticGameObjects")
    void testMoveByRespectingGameBoundariesWithinBounds(GameObject gameObject) {
        double xAmount = 10;
        double yAmount = 10;
        double objectWidth = gameObject.getWidth();
        double objectHeight = gameObject.getHeight();

        double xOld = gameObject.getX();
        double yOld = gameObject.getY();

        // Act
        gameObject.moveByRespectingGameBoundaries(xAmount, yAmount, objectWidth, objectHeight);

        // Assert
        assertEquals(xOld + xAmount, gameObject.getX());
        assertEquals(yOld + yAmount, gameObject.getY());
    }

    @ParameterizedTest
    @MethodSource("getStaticGameObjects")
    void testMoveByRespectingGameBoundariesOutsideBounds(GameObject gameObject) {
        double xAmount = 10;
        double yAmount = 10000; // Move outside vertical bounds
        double objectWidth = gameObject.getWidth();
        double objectHeight = gameObject.getHeight();

        double xOld = gameObject.getX();
        double yOld = gameObject.getY();

        // Act
        gameObject.moveByRespectingGameBoundaries(xAmount, yAmount, objectWidth, objectHeight);

        // Assert
        assertEquals(xOld, gameObject.getX());
        assertEquals(yOld, gameObject.getY());
    }
}
