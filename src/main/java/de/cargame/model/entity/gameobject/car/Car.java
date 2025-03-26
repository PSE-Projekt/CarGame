package de.cargame.model.entity.gameobject.car;

import de.cargame.controller.entity.GameMode;
import de.cargame.model.entity.gameobject.Coordinate;
import de.cargame.model.entity.gameobject.Dimension;
import de.cargame.model.entity.gameobject.GameObject;
import de.cargame.model.entity.gameobject.GameObjectBoundType;
import lombok.Getter;

/**
 * Represents an abstract car entity within the game.
 * This class serves as a base type for various implementations of cars, such as player-controlled or AI-controlled cars.
 * A Car object is a specialized form of GameObject with speed as an additional attribute.
 * <p>
 * Subclasses of Car are responsible for defining specific behaviors such as:
 * - Adjusting the speed of the car via the `setSpeed` method.
 * - Configuring additional properties related to their specific implementations.
 */
@Getter
public abstract class Car extends GameObject {

    protected double speed;

    protected Car(Coordinate coordinate, Dimension dimension, GameObjectBoundType gameObjectBoundType, GameMode gameMode) {
        super(coordinate, dimension, gameObjectBoundType, gameMode);
    }

    @Override
    protected void setIsStatic() {
        this.isStatic = false;
    }

    protected abstract void setSpeed();


    @Override
    public boolean equals(Object o) {
        return super.equals(o);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }
}
