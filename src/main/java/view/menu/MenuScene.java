package view.menu;

import javafx.scene.layout.VBox;
import view.CustomScene;
import view.navigation.Direction;
import view.navigation.Navigator;
import view.navigation.Selectable;

public class MenuScene extends CustomScene {
    private Navigator assignedNavigator;

    public MenuScene(APIHandler apiHandler){
        super();
        this.assignedNavigator = new MenuNavigator(apiHandler);
        Selectable dummy = assignedNavigator.getCurrentSelectable();
        Selectable singlePlayer = new MultiPlayerButton();
        Selectable multiPlayer = new SinglePlayerButton();
        
        dummy.setNeighbour(Direction.LEFT, singlePlayer);
        dummy.setNeighbour(Direction.RIGHT, multiPlayer);

        singlePlayer.setNeighbour(Direction.RIGHT, multiPlayer);
        multiPlayer.setNeighbour(Direction.LEFT, singlePlayer);

    }

    @Override
    public void render() {
        //TODO
    }
}
