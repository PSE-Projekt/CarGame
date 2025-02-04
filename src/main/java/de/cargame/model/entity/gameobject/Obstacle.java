package de.cargame.model.entity.gameobject;

/**
 * Represents an obstacle object within the game world, extending from {@link GameObject}.
 * Obstacles are stationary objects that flow in the game scene and interact with the players car
 * as collidable objects.
 */
public class Obstacle extends GameObject {

    public Obstacle(Coordinate coordinate, Dimension dimension, GameObjectBoundType gameObjectBoundType) {
        super(coordinate, dimension, gameObjectBoundType);
    }


    /**
     * Moves the object based on the elapsed time and speed configuration.
     * The movement is influenced by the game's normal speed or fast forward speed,
     * depending on whether fast-forwarding is active.
     *
     * @param deltaTime        The time elapsed since the last update, used to compute the distance to move.
     * @param isFastForwarding A boolean indicating if the game is in fast forward mode,
     *                         which affects the movement speed.
     */
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
        this.isCollidable = true;
    }
}
