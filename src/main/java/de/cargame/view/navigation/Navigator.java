package de.cargame.view.navigation;

import de.cargame.controller.input.UserInputBundle;
import de.cargame.view.ApiHandler;
import lombok.Getter;
import lombok.Setter;

public abstract class Navigator {
    protected final ApiHandler apiHandler;
    @Getter
    private final Selectable initialSelectable;
    @Setter
    @Getter
    protected Selectable currentSelection;

    public Navigator(ApiHandler apiHandler) {
        this.currentSelection = new DummySelectable();
        this.initialSelectable = this.currentSelection;
        this.apiHandler = apiHandler;
    }

    public abstract void receiveInput(UserInputBundle userInputBundle, String playerID);
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
