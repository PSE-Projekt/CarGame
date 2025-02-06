package de.cargame.model.entity.gameobject;

import java.awt.*;


/**
 * Represents a rectangular boundary for a game object within the game world.
 * This class extends the {@code GameObjectBound} class and defines the spatial boundaries
 * in the form of a rectangle. It uses a {@code Dimension} object to specify the width
 * and height of the boundary.
 * <p>
 * This boundary can be manipulated by moving its position and can be retrieved
 * as a {@code Shape} object.
 */
public class RectangularGameObjectBound extends GameObjectBound {
    private final Dimension dimension;


    public RectangularGameObjectBound(double x, double y, int width, int height) {
        super(x, y);
        dimension = new Dimension(width, height);
        coordinate = new Coordinate(x, y);
    }

    @Override
    public Shape getBound() {
        double x = coordinate.getX();
        double y = coordinate.getY();
        int height = dimension.getHeight();
        int width = dimension.getWidth();

        return new Rectangle((int) x, (int) y, width, height);
    }
}
