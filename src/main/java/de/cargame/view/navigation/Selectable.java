package de.cargame.view.navigation;

import javafx.scene.layout.Pane;

import java.util.HashMap;
import java.util.Map;

public abstract class Selectable extends Pane {

    private boolean selected;

    private final Map<Direction,Selectable> neighbours =  new HashMap<>();

    public Selectable(){
        neighbours.put(Direction.UP, null);
        neighbours.put(Direction.RIGHT, null);
        neighbours.put(Direction.DOWN, null);
        neighbours.put(Direction.LEFT, null);
    }

    public void setNeighbour(Direction dir, Selectable neighbour){
        neighbours.put(dir, neighbour);
    }

    public Selectable getNeighbour(Direction direction){
        return neighbours.get(direction);
    }

    public abstract void deselect();

    public abstract void select();
}
