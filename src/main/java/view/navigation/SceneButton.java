package view.navigation;

public abstract class SceneButton extends Selectable implements Clickable{
//TODO
    /*
    private image defaultDisplay;
    private image displayOnSelection;
    private image display;

*/

    @Override
    public abstract void onClick(Object apiHandler, String playerID);

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
