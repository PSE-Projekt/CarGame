package de.cargame.view.navigation;

import de.cargame.view.ApiHandler;

public abstract class Navigator {
    private Selectable currentSelection;

    public Navigator(ApiHandler apiHandler) {
        this.currentSelection = new DummySelectable();
    }

    public Selectable getCurrentSelectable() {
        return currentSelection;
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
