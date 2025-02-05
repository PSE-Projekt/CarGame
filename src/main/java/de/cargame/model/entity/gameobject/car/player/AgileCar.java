package de.cargame.model.entity.gameobject.car.player;

import de.cargame.config.ConfigKey;
import de.cargame.config.GameConfigService;
import de.cargame.controller.entity.GameMode;
import de.cargame.model.entity.gameobject.Coordinate;
import de.cargame.model.entity.gameobject.Dimension;
import de.cargame.model.entity.gameobject.GameObjectBoundType;

/**
 * Represents a specialized type of PlayerCar with enhanced agility.
 * The AgileCar is designed to provide faster movement
 * and distinct maneuverability characteristics.
 * <p>
 * * The AgileCar is designed to offer slower movement speed
 * * with faster inertia than a standard player car.
 */
public class AgileCar extends PlayerCar {

    private final double AGILE_CAR_SPEED;
    private final double AGILE_CAR_INERTIA;

    public AgileCar(Coordinate coordinate, Dimension dimension, GameObjectBoundType gameObjectBoundType, GameMode gameMode) {
        super(coordinate, dimension, gameObjectBoundType, gameMode);
        AGILE_CAR_SPEED = GameConfigService.getInstance().loadDouble(ConfigKey.AGILE_CAR_SPEED);
        AGILE_CAR_INERTIA = GameConfigService.getInstance().loadDouble(ConfigKey.AGILE_CAR_INERTIA);
        setSpeed();
        setInertia(AGILE_CAR_INERTIA);
    }

    @Override
    protected void setSpeed() {
        this.speed = AGILE_CAR_SPEED;
    }
}
