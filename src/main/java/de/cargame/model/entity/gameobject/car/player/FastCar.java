package de.cargame.model.entity.gameobject.car.player;

import de.cargame.config.GameConfig;
import de.cargame.model.entity.gameobject.Coordinate;
import de.cargame.model.entity.gameobject.Dimension;
import de.cargame.model.entity.gameobject.GameObjectBoundType;

/**
 * Represents a type of player-controlled car with enhanced speed capabilities.
 * The FastCar class extends the PlayerCar class, providing specific attributes
 * and behaviors relevant to a high-speed / high-inertia car.
 * <p>
 * The FastCar is designed to offer faster movement speed
 * with slower inertia than a standard player car.
 */
public class FastCar extends PlayerCar {

    public FastCar(Coordinate coordinate, Dimension dimension, GameObjectBoundType gameObjectBoundType) {
        super(coordinate, dimension, gameObjectBoundType);
        setSpeed();
        setInertia(GameConfig.FAST_CAR_INERTIA);
    }

    @Override
    protected void setSpeed() {
        this.speed = GameConfig.FAST_CAR_SPEED;
    }
}
