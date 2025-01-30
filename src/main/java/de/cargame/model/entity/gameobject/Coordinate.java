package de.cargame.model.entity.gameobject;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * Represents a coordinate in a 2D space.
 * <p>
 * This class provides a way to capture and manipulate a point
 * defined by its x and y coordinates in a two-dimensional plane.
 **/
@Getter
@Setter
@AllArgsConstructor
public class Coordinate {
    private double x;
    private double y;


    /**
     * Adds the specified amount to the x-coordinate of this coordinate.
     *
     * @param xAmount The value to be added to the current x-coordinate.
     */
    public void addX(double xAmount) {
        x += xAmount;
    }


    /**
     * Adds the specified amount to the y-coordinate of this coordinate.
     *
     * @param yAmount The value to be added to the current y-coordinate.
     */
    public void addY(double yAmount) {
        y += yAmount;
    }


    /**
     * Returns a string representation of the coordinate in the format (x, y),
     * where x and y are the x-coordinate and y-coordinate values respectively.
     *
     * @return A string representing the coordinate in the form "(x,y)".
     */
    @Override
    public String toString() {
        return "(" + getX() + "," + getY() + ")";
    }

}
