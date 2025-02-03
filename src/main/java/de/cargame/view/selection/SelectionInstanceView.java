package de.cargame.view.selection;

import de.cargame.config.GameConfig;
import de.cargame.controller.api.PlayerAPI;
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
    private final String playerId;
    private final ApiHandler apiHandler;


    public SelectionInstanceView(ApiHandler apiHandler, String playerId){
        this.assignedNavigator = new SelectionNavigator(apiHandler);
        this.apiHandler = apiHandler;

        backToMenuButton = new BackToMenuButton();
        fastCarButton = new CarSelectionPanel();
        agileCarButton = new CarSelectionPanel();
        this.playerId = playerId;

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

    public void setup() {
        PlayerAPI playerApi = this.apiHandler.getPlayerApi();

        if (this.playerId.equals(playerApi.getGamepadPlayerId())) {
            this.apiHandler.getInputReceiverGamePad().assignNavigator(assignedNavigator);
        } else if (this.playerId.equals(playerApi.getKeyboardPlayerId())) {
            this.apiHandler.getInputReceiverKeyboard().assignNavigator(assignedNavigator);
        } else {
            throw new IllegalStateException("the id of this selection instance belongs to no player");
        }

        assignedNavigator.reset();
    }
}
