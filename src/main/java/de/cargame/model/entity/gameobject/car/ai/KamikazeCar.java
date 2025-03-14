package de.cargame.model.entity.gameobject.car.ai;

import de.cargame.controller.entity.GameMode;
import de.cargame.model.entity.gameobject.Coordinate;
import de.cargame.model.entity.gameobject.Dimension;
import de.cargame.model.entity.gameobject.GameObjectBoundType;

/**
 * Represents a specific type of AI-controlled car with kamikaze-like behavior.
 * The KamikazeCar class extends the functionality of {@link AICar} and specializes
 * in aggressive movement towards a target, following its associated {@link MovementStrategy}.
 * <p>
 * A KamikazeCar dynamically moves towards a target position using the speed,
 * direction, and time delta provided in the game environment. The speed is set
 * to a predefined value specific to AI cars and adjusts based on whether the
 * game is in fast-forward mode or not.
 */
public class KamikazeCar extends AICar {

    public KamikazeCar(Coordinate coordinate, Dimension dimension, GameObjectBoundType gameObjectBoundType, MovementStrategy movementStrategy, GameMode gameMode) {
        super(coordinate, dimension, gameObjectBoundType, movementStrategy, gameMode);
        setSpeed();
    }

    /**
     * Moves the KamikazeCar instance based on its speed, direction, and movement strategy.
     * The movement is influenced by the provided time delta and whether the game is in
     * fast-forward mode. The movement is calculated towards the target position provided
     * by the associated {@link de.cargame.model.entity.gameobject.car.ai.MovementStrategy}.
     *
     * @param deltaTime        The elapsed time since the last movement update, used to calculate
     *                         the distance the car should move.
     * @param isFastForwarding A flag indicating whether the game is running in fast-forward
     *                         mode, affecting the speed of the car accordingly.
     */
    @Override
    public void move(double deltaTime, boolean isFastForwarding) {
        double aiCarSpeed;
        if (isFastForwarding) {
            aiCarSpeed = getSpeed() * GAME_SPEED_FAST_FORWARD;
        } else {
            aiCarSpeed = getSpeed() * GAME_SPEED;
        }
        MovementStrategy movementStrategy = getMovementStrategy();

        double deltaX = movementStrategy.getTargetPosX() - getX();
        double deltaY = movementStrategy.getTargetPosY() - getY();
        double distance = Math.sqrt(deltaX * deltaX + deltaY * deltaY);

        double directionX = deltaX / distance;
        double directionY = deltaY / distance;

        double moveX = directionX * getSpeed() * deltaTime * aiCarSpeed;
        double moveY = directionY * getSpeed() * deltaTime * aiCarSpeed;

        moveBy(moveX, moveY);

    }


    @Override
    protected void setSpeed() {
        this.speed = AI_CAR_SPEED;
    }
}
