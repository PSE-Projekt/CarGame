package de.cargame.view.navigation;

import de.cargame.view.ApiHandler;
import lombok.Getter;
import lombok.Setter;

public abstract class Navigator {
    @Getter
    private final Selectable initialSelectable;
    @Setter
    @Getter
    private Selectable currentSelection;

    public Navigator(ApiHandler apiHandler) {
        this.currentSelection = new DummySelectable();
        this.initialSelectable = this.currentSelection;
    }

    public abstract void receiveInput(Object userInputBundle, String playerID);
    /*TODO
    if(UserInputBundle.getRecent matches Direction){
        Direction dir = UserInputBundle.getRecent();
    }
    if(currentSelection.getNeighbour(dir) != null){
        currentSelection = currentSelection.getNeighbour();
    }*/

    private void switchSelected(){

    }

    private void callSelected(){

    }
}
