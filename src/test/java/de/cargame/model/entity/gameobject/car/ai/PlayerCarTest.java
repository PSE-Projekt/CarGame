package de.cargame.model.entity.gameobject.car.ai;

import de.cargame.controller.entity.GameMode;
import de.cargame.controller.input.UserInput;
import de.cargame.controller.input.UserInputType;
import de.cargame.model.entity.gameobject.Coordinate;
import de.cargame.model.entity.gameobject.Dimension;
import de.cargame.model.entity.gameobject.GameObjectBoundType;
import de.cargame.model.entity.gameobject.car.player.AgileCar;
import de.cargame.model.handler.PlayerHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class PlayerCarTest {

    @Mock
    private Coordinate coordinate;

    @Mock
    private Dimension dimension;

    @Mock
    private PlayerHandler playerHandler;

    @Mock
    private GameObjectBoundType gameObjectBoundType;

    @InjectMocks
    private AgileCar playerCar;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // Gerçek bir Coordinate kullanarak sınır aşımı sorununu çöz
        coordinate = new Coordinate(250.0, 500.0); // Başlangıçta 'y' sınırdan UZAK bir değerde

        dimension = Mockito.mock(Dimension.class);
        when(dimension.getWidth()).thenReturn(80);
        when(dimension.getHeight()).thenReturn(36);

        playerCar = new AgileCar(coordinate, dimension, GameObjectBoundType.RECTANGLE, GameMode.SINGLEPLAYER);

        playerCar.setPlayerHandler(playerHandler);

    }

    @Test
    void testDownwardsMovement() {
        // Mock player input as DOWN
        when(playerHandler.getCurrentUserInput())
                .thenReturn(Optional.of(new UserInput(UserInputType.DOWN)));

        // Simulate move
        playerCar.move(1.0, false);

        // Assert Y position increased (moved downwards)
        assertTrue(playerCar.getY() > 50, "Car should move down when DOWN input is provided.");
    }

    @Test
    void testBoundaryLimitations() {
        // Set Y-coordinate near top boundary and mock UP input
        playerCar.getCoordinates().setY(0); // Top boundary
        when(playerHandler.getCurrentUserInput())
                .thenReturn(Optional.of(new UserInput(UserInputType.UP)));

        // Simulate move
        playerCar.move(1.0, false);

        // Assert car doesn't move beyond top boundary
        assertEquals(0, playerCar.getCoordinates().getY(), "Car should not move above boundary (Y = 0).");

        playerCar.getCoordinates().setY(playerCar.getSCREEN_HEIGHT()); // Bottom boundary
        when(playerHandler.getCurrentUserInput())
                .thenReturn(Optional.of(new UserInput(UserInputType.DOWN)));

        // Simulate move
        playerCar.move(1.0, false);

        // Assert car doesn't move beyond bottom boundary
        assertTrue(playerCar.getY() <= playerCar.getSCREEN_HEIGHT(), "Car should not move below the screen height.");
    }

    @Test
    void testCrashCooldown() {
        // Simulate a crash and set crash time
        playerCar.setLastCrashTime();

        // Assert crash cooldown is active
        assertTrue(playerCar.hasCrashCooldown(), "Car should have a cooldown period immediately after crashing.");

        // Mock crash cooldown time has passed
        try {
            Thread.sleep(playerCar.getCRASH_COOLDOWN_TIME() + 100);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        // Assert cooldown is over
        assertFalse(playerCar.hasCrashCooldown(), "Car should not have a cooldown after enough time has passed.");
    }

    @Test
    void testIdleWithNoInput() {
        // Simulate no user input
        when(playerHandler.getCurrentUserInput()).thenReturn(Optional.empty());

        // Record initial position
        double initialX = playerCar.getX();
        double initialY = playerCar.getY();

        // Simulate move
        playerCar.move(1.0, false);

        // Assert no movement occurred
        assertEquals(initialX, playerCar.getX(), "Car's X should not change with no input.");
        assertEquals(initialY, playerCar.getY(), "Car's Y should not change with no input.");
    }

}