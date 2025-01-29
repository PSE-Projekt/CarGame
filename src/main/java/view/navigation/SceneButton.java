package view.navigation;

import view.ApiHandler;

public abstract class SceneButton extends Selectable implements Clickable{
//TODO
    /*
    private image defaultDisplay;
    private image displayOnSelection;
    private image display;

*/

    @Override
    public abstract void onClick(ApiHandler apiHandler, String playerID);

    @Override
    public void select() {
        toggleSelected();
      //  display = displayOnSelection;
    }

    @Override
    public void deselect(){
        toggleSelected();
      //  display = defaultDisplay;
    }
}
