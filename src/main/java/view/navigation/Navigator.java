package view.navigation;

import view.APIHandler;

public abstract class Navigator {
    private Selectable currentSelection;

    public Navigator(APIHandler apiHandler) {
        this.currentSelection = new DummySelectable(null, null, null, null);
    }

    public Selectable getCurrentSelectable() {
        return currentSelection;
    }
    public void receiveInput(Object userInputBundle, String playerID);
    if(UserInputBundle.getRecent matches Direction){
        Direction dir = UserInputBundle.getRecent();
    }
    if(currentSelection.getNeighbour(dir) != null){
        currentSelection = currentSelection.getNeighbour();
    }

    private void switchSelected(){

    }

    private void callSelected(){

    }
}
