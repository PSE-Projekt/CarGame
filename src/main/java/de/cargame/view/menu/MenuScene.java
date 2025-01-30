package de.cargame.view.menu;

import de.cargame.view.ApiHandler;
import de.cargame.view.CustomScene;
import de.cargame.view.navigation.Direction;
import de.cargame.view.navigation.Navigator;
import de.cargame.view.navigation.Selectable;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class MenuScene extends CustomScene {

    public MenuScene(ApiHandler apiHandler){
        super(apiHandler);
        Navigator assignedNavigator = new MenuNavigator(apiHandler);
        Selectable dummy = assignedNavigator.getCurrentSelectable();
        Selectable multiPlayerButton = new MultiPlayerButton();
        Selectable singlePlayerButton = new SinglePlayerButton();
        
        dummy.setNeighbour(Direction.LEFT, singlePlayerButton);
        dummy.setNeighbour(Direction.RIGHT, multiPlayerButton);

        singlePlayerButton.setNeighbour(Direction.RIGHT, multiPlayerButton);
        multiPlayerButton.setNeighbour(Direction.LEFT, singlePlayerButton);


        ((VBox)this.getRoot()).getChildren().add(new Text("Welcome to CarGame Menu!"));
    }

    @Override
    public void render() {
        //TODO
    }
}
