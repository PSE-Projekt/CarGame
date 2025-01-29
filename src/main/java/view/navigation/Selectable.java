package view.navigation;

import java.util.HashMap;
import java.util.Map;

public abstract class Selectable {

    private boolean selected;

    private Map<Direction,Selectable> neighbours =  new HashMap<>();

    public Selectable(){
        neighbours.put(Direction.UP, null);
        neighbours.put(Direction.RIGHT, null);
        neighbours.put(Direction.DOWN, null);
        neighbours.put(Direction.LEFT, null);
    }

    public void setNeighbour(Direction dir, Selectable neighbour){
        neighbours.put(dir, neighbour);
    }

    public void toggleSelected() {
        if(selected){
            selected = false;
        } else {
            selected = true;
        }
    }

    public void select() {
        toggleSelected();
    }

    public void deselect(){
        toggleSelected();
    }

    public Selectable getNeighbour(Direction direction){
        return neighbours.get(direction);
    }

}
