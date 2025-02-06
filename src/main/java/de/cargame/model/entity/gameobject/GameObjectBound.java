package de.cargame.model.entity.gameobject;

import lombok.Getter;

import java.awt.*;

/**
 * Represents a game object's boundary in the game world.
 * This class serves as an abstract base class to define the spatial location
 * and boundary of an object, as well as methods to move the object.
 * <p>
 * The coordinate property specifies the location of the boundary in the
 * game world, and subclasses implement the abstract getBound method to
 * define the specific bounding shape.
 */
public abstract class GameObjectBound {

    @Getter
    protected Coordinate coordinate;


    protected GameObjectBound(double x, double y) {
        coordinate = new Coordinate(x, y);
    }

    public abstract Shape getBound();


    public void moveBy(double xAmount, double yAmount) {
        coordinate.addX(xAmount);
        coordinate.addY(yAmount);
    }
}
