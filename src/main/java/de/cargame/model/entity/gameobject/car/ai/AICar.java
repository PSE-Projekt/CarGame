package de.cargame.model.entity.gameobject.car.ai;

import de.cargame.config.ConfigKey;
import de.cargame.config.GameConfigService;
import de.cargame.controller.entity.GameMode;
import de.cargame.model.entity.gameobject.Coordinate;
import de.cargame.model.entity.gameobject.Dimension;
import de.cargame.model.entity.gameobject.GameObjectBoundType;
import de.cargame.model.entity.gameobject.car.Car;
import lombok.Getter;

public abstract class AICar extends Car {

    protected final double AI_CAR_SPEED;
    @Getter
    private final MovementStrategy movementStrategy;


    protected AICar(Coordinate coordinate, Dimension dimension, GameObjectBoundType gameObjectBoundType, MovementStrategy movementStrategy, GameMode gameMode) {
        super(coordinate, dimension, gameObjectBoundType, gameMode);
        this.movementStrategy = movementStrategy;
        AI_CAR_SPEED = GameConfigService.getInstance().loadDouble(ConfigKey.AI_CAR_SPEED);
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
