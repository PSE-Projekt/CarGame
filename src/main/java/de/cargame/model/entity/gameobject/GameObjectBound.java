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

    /**
     * Retrieves the bounding shape that defines the spatial boundary of the object.
     * The specific shape of the boundary is determined by the subclass implementation.
     *
     * @return A {@code Shape} instance representing the boundary of the object.
     */
    public abstract Shape getBound();


    /**
     * Adjusts the position of the object by modifying its x and y coordinates.
     *
     * @param xAmount The amount to move the object along the x-axis.
     * @param yAmount The amount to move the object along the y-axis.
     */
    public void moveBy(double xAmount, double yAmount) {
        coordinate.addX(xAmount);
        coordinate.addY(yAmount);
    }
}
