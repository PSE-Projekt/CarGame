package de.cargame.view.navigation;

/**
 * DummyObject which will be selected on default in navigable scenes. Upon moving
 * to another (visible) button, the dummy will no longer be selectable.
 */
public class DummySelectable extends Selectable {
    /**
     * Creates a new Dummy object.
     */
    public DummySelectable() {
        super();
    }

    @Override
    public void deselect() {
        //empty because dummy
    }

    @Override
    public void select() {
        //empty because dummy
    }
}
