package de.cargame.model.entity.gameobject.car.ai;

import de.cargame.model.entity.gameobject.Coordinate;
import de.cargame.model.entity.gameobject.Dimension;
import de.cargame.model.entity.gameobject.GameObjectBoundType;

public class KamikazeCar extends AICar {

    public KamikazeCar(Coordinate coordinate, Dimension dimension, GameObjectBoundType gameObjectBoundType, MovementStrategy movementStrategy) {
        super(coordinate, dimension, gameObjectBoundType, movementStrategy);
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
