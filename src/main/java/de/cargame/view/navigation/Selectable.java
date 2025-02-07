package de.cargame.view.navigation;

import javafx.scene.layout.Pane;
import lombok.Setter;

import java.util.EnumMap;
import java.util.Map;

/**
 * Class for selectable objects in the User Interface. Provides methods for interaction with them.
 */
public abstract class Selectable extends Pane {

    private final Map<Direction, Selectable> neighbours = new EnumMap<>(Direction.class);
    @Setter
    private boolean lockedInSelection;

    /**
     * Initializes a new Selectable object as well as with it's variables.
     */
    protected Selectable() {
        neighbours.put(Direction.UP, null);
        neighbours.put(Direction.RIGHT, null);
        neighbours.put(Direction.DOWN, null);
        neighbours.put(Direction.LEFT, null);

        lockedInSelection = false;
    }

    /**
     * Sets the neighbor from the matching direction to the desired Selectable.
     *
     * @param neighbour desired neighbour
     * @param dir       desired direction
     */
    public void setNeighbour(Direction dir, Selectable neighbour) {
        neighbours.put(dir, neighbour);
    }

    /**
     * Returns neighbor from matching direction, otherwise null.
     */
    public Selectable getNeighbour(Direction direction) {
        if (lockedInSelection) {
            return null;
        }
        return neighbours.get(direction);
    }

    /**
     * Restores the button’s default appearance.
     */
    public abstract void deselect();

    /**
     * Changes the button’s appearance to the selected version.
     */
    public abstract void select();
}
