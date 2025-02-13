package de.cargame.model.entity.gameobject;

import de.cargame.controller.entity.GameMode;
import de.cargame.model.entity.player.Player;
import de.cargame.model.handler.PlayerHandler;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class LifeTest {

    @Test
    void collect_shouldIncreaseLifeAndReturnTrue_whenLifeNotCollected() {
        // Arrange
        Coordinate coordinate = new Coordinate(0, 0);
        Dimension dimension = new Dimension(10, 10);
        GameObjectBoundType gameObjectBoundType = GameObjectBoundType.RECTANGLE;
        GameMode gameMode = mock(GameMode.class);

        Player player = mock(Player.class);
        when(player.getId()).thenReturn("player1");

        PlayerHandler playerHandler = mock(PlayerHandler.class);
        when(playerHandler.getPlayer()).thenReturn(player);

        Life life = new Life(coordinate, dimension, gameObjectBoundType, gameMode);

        // Act
        boolean result = life.collect(playerHandler);

        // Assert
        assertTrue(result);
        verify(playerHandler).increaseLife();
        verify(playerHandler, atLeastOnce()).getPlayer();
        assertTrue(life.isCollected());
    }

}