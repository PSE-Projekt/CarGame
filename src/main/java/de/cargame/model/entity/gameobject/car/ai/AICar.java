package de.cargame.model.entity.gameobject.car.ai;

import de.cargame.model.entity.gameobject.Coordinate;
import de.cargame.model.entity.gameobject.Dimension;
import de.cargame.model.entity.gameobject.GameObjectBoundType;
import de.cargame.model.entity.gameobject.car.Car;
import lombok.Getter;

public abstract class AICar extends Car {

    @Getter
    private final MovementStrategy movementStrategy;


    public AICar(Coordinate coordinate, Dimension dimension, GameObjectBoundType gameObjectBoundType, MovementStrategy movementStrategy) {
        super(coordinate, dimension, gameObjectBoundType);
        this.movementStrategy = movementStrategy;
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
