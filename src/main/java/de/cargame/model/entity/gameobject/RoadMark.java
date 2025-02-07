package de.cargame.model.entity.gameobject;

import de.cargame.controller.entity.GameMode;

/**
 * RoadMark represents a static visual marker in the game world typically used to indicate
 * road lines or boundaries within the game. This class is a specialized implementation
 * of the {@link GameObject} class.
 * <p>
 * Responsibilities of RoadMark:
 * - Remains static without interaction as a collidable object.
 * - Moves horizontally with the game environment to simulate game progression.
 * - Automatically despawns when no longer visible in the game world.
 * <p>
 * Behavior and Key Attributes:
 * - The `move` method controls its horizontal movement based on game speed.
 * - The object is not collidable, ensuring it serves purely as a visual element.
 * - The object is marked as despawnable once it moves off-screen to allow for resource cleanup.
 */
public class RoadMark extends GameObject {


    public RoadMark(Coordinate coordinate, Dimension dimension, GameObjectBoundType gameObjectBoundType, GameMode gameMode) {
        super(coordinate, dimension, gameObjectBoundType, gameMode);
    }

    @Override
    public void move(double deltaTime, boolean isFastForwarding) {
        if (isFastForwarding) {
            moveBy(-GAME_SPEED_FAST_FORWARD * deltaTime, 0);
        } else {
            moveBy(-GAME_SPEED * deltaTime, 0);
        }
    }

    @Override
    protected void setIsStatic() {
        this.isStatic = true;
    }

    @Override
    protected void setDespawnable() {
        this.isDespawnable = true;
    }

    @Override
    protected void setCollidable() {
        this.isCollidable = false;
    }
}
