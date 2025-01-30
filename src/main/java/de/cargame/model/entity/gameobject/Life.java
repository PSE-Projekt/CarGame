package de.cargame.model.entity.gameobject;

import de.cargame.model.handler.PlayerHandler;

/**
 * Represents a "Life" reward in the game.
 * A "Life" object provides additional lives to the player when collected.
 * This class extends the {@code Reward} class and inherits its behavior and properties.
 * <p>
 * Attributes such as position, dimension, and boundary type are defined during instantiation.
 * The position is represented by coordinates, the size is determined by its dimensions,
 * and the boundary type governs its collision detection.
 */
public class Life extends Reward {

    public Life(Coordinate coordinate, Dimension dimension, GameObjectBoundType gameObjectBoundType) {
        super(coordinate, dimension, gameObjectBoundType);
    }

    @Override
    public boolean collect(PlayerHandler playerHandler) {
        if (!isCollected()) {
            playerHandler.increaseLife();
            setCollected(true);
            return true;
        }
        return false;
    }
}
