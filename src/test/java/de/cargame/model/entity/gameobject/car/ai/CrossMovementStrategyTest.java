package de.cargame.model.entity.gameobject.car.ai;

import de.cargame.controller.entity.GameMode;
import de.cargame.model.entity.gameobject.Coordinate;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class CrossMovementStrategyTest {

    @Test
    void testCalcTargetPositionSinglePlayer() throws NoSuchFieldException, IllegalAccessException {
        Random mockedRandom = mock(Random.class);
        when(mockedRandom.nextInt(anyInt())).thenReturn(300); // Mock random value

        Coordinate spawnCoordinate = new Coordinate(0, 0);
        CrossMovementStrategy strategy = new CrossMovementStrategy(spawnCoordinate, GameMode.SINGLEPLAYER);
        Field random = AICarMovementStrategy.class.getDeclaredField("random");
        random.setAccessible(true);
        random.set(strategy, mockedRandom);

        strategy.calcTargetPos();

        assertEquals(new Coordinate(-1366, 300), strategy.targetPos);
    }
}