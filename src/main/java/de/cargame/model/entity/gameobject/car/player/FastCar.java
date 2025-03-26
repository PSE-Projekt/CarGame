package de.cargame.model.entity.gameobject.car.player;

import de.cargame.config.ConfigKey;
import de.cargame.config.GameConfigService;
import de.cargame.controller.entity.GameMode;
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

    private final double FAST_CAR_SPEED;
    private final double FAST_CAR_INERTIA;

    public FastCar(Coordinate coordinate, Dimension dimension, GameObjectBoundType gameObjectBoundType, GameMode gameMode) {
        super(coordinate, dimension, gameObjectBoundType, gameMode);
        FAST_CAR_SPEED = GameConfigService.getInstance().loadDouble(ConfigKey.FAST_CAR_SPEED);
        FAST_CAR_INERTIA = GameConfigService.getInstance().loadDouble(ConfigKey.FAST_CAR_INERTIA);
        setSpeed();
        setInertia(FAST_CAR_INERTIA);
    }

    @Override
    protected void setSpeed() {
        this.speed = FAST_CAR_SPEED;
    }

    @Override
    public boolean equals(Object o) {
        return super.equals(o);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }
}
