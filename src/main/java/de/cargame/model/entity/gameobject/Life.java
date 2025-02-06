package de.cargame.model.entity.gameobject;

import de.cargame.controller.entity.GameMode;
import de.cargame.model.handler.PlayerHandler;
import lombok.extern.slf4j.Slf4j;

/**
 * Represents a "Life" reward in the game.
 * A "Life" object provides additional lives to the player when collected.
 * This class extends the {@code Reward} class and inherits its behavior and properties.
 * <p>
 * Attributes such as position, dimension, and boundary type are defined during instantiation.
 * The position is represented by coordinates, the size is determined by its dimensions,
 * and the boundary type governs its collision detection.
 */
@Slf4j
public class Life extends Reward {

    public Life(Coordinate coordinate, Dimension dimension, GameObjectBoundType gameObjectBoundType, GameMode gameMode) {
        super(coordinate, dimension, gameObjectBoundType, gameMode);
    }

    @Override
    public boolean collect(PlayerHandler playerHandler) {
        String playerId = playerHandler.getPlayer().getId();
        int lifeCount = playerHandler.getLifeCount();
        if (!isCollected()) {
            playerHandler.increaseLife();
            setCollected(true);
            log.info("Player {} collected a life. New life count: {}", playerId, lifeCount);
            return true;
        }
        return false;
    }
}
