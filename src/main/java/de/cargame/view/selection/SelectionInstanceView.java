package de.cargame.view.selection;

import de.cargame.view.ApiHandler;
import de.cargame.view.common.BackToMenuButton;
import de.cargame.view.menu.MenuNavigator;
import de.cargame.view.navigation.Direction;
import de.cargame.view.navigation.Navigator;
import de.cargame.view.navigation.Selectable;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class SelectionInstanceView { //TODO:could also extend Pane? was meinst du Malik?

    public SelectionInstanceView(ApiHandler apiHandler){
        Navigator assignedNavigator = new SelectionNavigator(apiHandler);
        Selectable dummy = assignedNavigator.getCurrentSelection();


        Selectable backToMenuButton = new BackToMenuButton();
        Selectable fastCarButton = new CarSelectionPanel();
        Selectable agileCarButton = new CarSelectionPanel();

        dummy.setNeighbour(Direction.LEFT, fastCarButton);
        dummy.setNeighbour(Direction.RIGHT, agileCarButton);

        fastCarButton.setNeighbour(Direction.RIGHT, agileCarButton);
        fastCarButton.setNeighbour(Direction.DOWN, backToMenuButton);

        agileCarButton.setNeighbour(Direction.LEFT, fastCarButton);
        agileCarButton.setNeighbour(Direction.DOWN, backToMenuButton);

        backToMenuButton.setNeighbour(Direction.UP, fastCarButton);

        Text menuText = new Text("CarGame");
        menuText.setStyle("-fx-font-size: 30px; -fx-font-weight: bold; -fx-fill: #009783;");
        menuText.setFont(Font.loadFont(getClass().getResourceAsStream("/frontend/monomaniacOne.ttf"), 30));
    }
}
