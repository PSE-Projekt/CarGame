package de.cargame.view.selection;

import de.cargame.config.GameConfig;
import de.cargame.view.ApiHandler;
import de.cargame.view.common.BackToMenuButton;
import de.cargame.view.navigation.Direction;
import de.cargame.view.navigation.Navigator;
import de.cargame.view.navigation.Selectable;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class SelectionInstanceView extends Pane {
    private final Navigator assignedNavigator;

    private final Selectable backToMenuButton;
    private final Selectable fastCarButton;
    private final Selectable agileCarButton;
    public SelectionInstanceView(ApiHandler apiHandler){

        this.assignedNavigator = new SelectionNavigator(apiHandler);

        backToMenuButton = new BackToMenuButton();
        fastCarButton = new CarSelectionPanel();
        agileCarButton = new CarSelectionPanel();

        preparePaneContents();
    }

    private void preparePaneContents() {
        fastCarButton.setNeighbour(Direction.RIGHT, agileCarButton);
        fastCarButton.setNeighbour(Direction.DOWN, backToMenuButton);

        agileCarButton.setNeighbour(Direction.LEFT, fastCarButton);
        agileCarButton.setNeighbour(Direction.DOWN, backToMenuButton);

        backToMenuButton.setNeighbour(Direction.UP, fastCarButton);

        assignedNavigator.getInitialSelectable().setNeighbour(Direction.LEFT, fastCarButton);
        assignedNavigator.getInitialSelectable().setNeighbour(Direction.RIGHT, agileCarButton);

        Text menuText = new Text("CarGame");
        menuText.setStyle("-fx-font-size: 30px; -fx-font-weight: bold; -fx-fill: #009783;");
        menuText.setFont(Font.loadFont(getClass().getResourceAsStream("/frontend/monomaniacOne.ttf"), 30));
        this.setHeight((double) GameConfig.SCREEN_HEIGHT / 2);
        this.setWidth(GameConfig.SCREEN_WIDTH);

        this.getChildren().addAll(backToMenuButton, fastCarButton, menuText, agileCarButton);
    }

    public void setup(){
        assignedNavigator.reset();
    }
}
