package de.cargame.view.navigation;

import javafx.scene.layout.Pane;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

public abstract class Selectable extends Pane {

    private final Map<Direction, Selectable> neighbours = new HashMap<>();
    @Setter
    private boolean lockedInSelection;

    public Selectable() {
        neighbours.put(Direction.UP, null);
        neighbours.put(Direction.RIGHT, null);
        neighbours.put(Direction.DOWN, null);
        neighbours.put(Direction.LEFT, null);

        lockedInSelection = false;
    }

    public void setNeighbour(Direction dir, Selectable neighbour) {
        neighbours.put(dir, neighbour);
    }

    public Selectable getNeighbour(Direction direction) {
        if (lockedInSelection) {
            return null;
        }
        return neighbours.get(direction);
    }


    public abstract void deselect();

    public abstract void select();
}
