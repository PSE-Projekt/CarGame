package de.cargame.view.selection;

import de.cargame.config.ConfigKey;
import de.cargame.config.GameConfig;
import de.cargame.config.GameConfigService;
import de.cargame.controller.api.PlayerAPI;
import de.cargame.model.entity.gameobject.car.player.CarType;
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
    private final SelectionScene handlingScene;

    private final Selectable backToMenuButton;
    private final Selectable fastCarButton;
    private final Selectable agileCarButton;
    private final String playerId;
    private final ApiHandler apiHandler;

    private boolean carChoiceMade = false;

    private final int SCREEN_WIDTH;
    private final int SCREEN_HEIGHT;


    public SelectionInstanceView(SelectionScene handlingScene, ApiHandler apiHandler, String playerId){

        SCREEN_WIDTH = GameConfigService.getInstance().loadInteger(ConfigKey.SCREEN_WIDTH);
        SCREEN_HEIGHT = GameConfigService.getInstance().loadInteger(ConfigKey.SCREEN_HEIGHT);

        this.assignedNavigator = new SelectionNavigator(apiHandler);
        this.handlingScene = handlingScene;
        this.apiHandler = apiHandler;

        backToMenuButton = new BackToMenuButton();
        fastCarButton = new CarSelectionPanel(CarType.FAST_CAR, this);
        agileCarButton = new CarSelectionPanel(CarType.AGILE_CAR, this);
        this.playerId = playerId;

        preparePaneContents();
    }

    boolean isReady(){
        return carChoiceMade;
    }

    void confirmChoice(){
        carChoiceMade = true;
        handlingScene.proceedToGame();
    }

    private void preparePaneContents() {
        fastCarButton.setNeighbour(Direction.RIGHT, agileCarButton);
        fastCarButton.setNeighbour(Direction.DOWN, backToMenuButton);

        agileCarButton.setNeighbour(Direction.LEFT, fastCarButton);
        agileCarButton.setNeighbour(Direction.DOWN, backToMenuButton);

        backToMenuButton.setNeighbour(Direction.UP, fastCarButton);

        assignedNavigator.getInitialSelectable().setNeighbour(Direction.LEFT, fastCarButton);
        assignedNavigator.getInitialSelectable().setNeighbour(Direction.RIGHT, agileCarButton);

        Text menuText = new Text("CarSelection");
        menuText.setStyle("-fx-font-size: 30px; -fx-font-weight: bold; -fx-fill: #009783;");
        menuText.setFont(Font.loadFont(getClass().getResourceAsStream("/frontend/monomaniacOne.ttf"), 30));
        this.setHeight((double) SCREEN_HEIGHT / 2);
        this.setWidth(SCREEN_WIDTH);

        this.getChildren().addAll(backToMenuButton, fastCarButton, menuText, agileCarButton);
    }

    public void setup() {
        PlayerAPI playerApi = this.apiHandler.getPlayerApi();
        carChoiceMade = false;
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
