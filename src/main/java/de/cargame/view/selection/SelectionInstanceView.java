package de.cargame.view.selection;

import de.cargame.config.ConfigKey;
import de.cargame.config.GameConfigService;
import de.cargame.controller.api.PlayerAPI;
import de.cargame.model.entity.gameobject.car.player.CarType;
import de.cargame.view.ApiHandler;
import de.cargame.view.common.BackToMenuButton;
import de.cargame.view.config.ColorConfig;
import de.cargame.view.config.TextConfig;
import de.cargame.view.navigation.Direction;
import de.cargame.view.navigation.Navigator;
import de.cargame.view.navigation.Selectable;
import javafx.geometry.Pos;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
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
    private final int SCREEN_WIDTH;
    private final int SCREEN_HEIGHT;
    private boolean carChoiceMade = false;


    public SelectionInstanceView(SelectionScene handlingScene, ApiHandler apiHandler, String playerId) {

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

    boolean isReady() {
        return carChoiceMade;
    }

    void confirmChoice() {
        carChoiceMade = true;
        handlingScene.proceedToGame();
    }

    private void preparePaneContents() {
        fastCarButton.setNeighbour(Direction.LEFT, agileCarButton);
        fastCarButton.setNeighbour(Direction.DOWN, backToMenuButton);

        agileCarButton.setNeighbour(Direction.RIGHT, fastCarButton);
        agileCarButton.setNeighbour(Direction.DOWN, backToMenuButton);

        backToMenuButton.setNeighbour(Direction.UP, agileCarButton);

        assignedNavigator.getInitialSelectable().setNeighbour(Direction.LEFT, agileCarButton);
        assignedNavigator.getInitialSelectable().setNeighbour(Direction.RIGHT, fastCarButton);
        assignedNavigator.getInitialSelectable().setNeighbour(Direction.DOWN, backToMenuButton);


        VBox sceneContent = new VBox();
        sceneContent.setStyle("-fx-background-color: #131d34;");
        sceneContent.setPrefSize(SCREEN_WIDTH, (double) SCREEN_HEIGHT / 2);
        sceneContent.setAlignment(Pos.CENTER);

        StackPane titleContainer = new StackPane();
        Text menuText = TextConfig.makeH1("Car Selection");
        menuText.setFill(ColorConfig.PRIMARY_MAIN);
        titleContainer.getChildren().add(menuText);
        titleContainer.setPrefSize(SCREEN_WIDTH, sceneContent.getPrefHeight() / 3);

        HBox selectionContainer = new HBox(30);
        selectionContainer.setPrefSize(SCREEN_WIDTH, sceneContent.getPrefHeight() / 3);
        selectionContainer.getChildren().addAll(agileCarButton, menuText, fastCarButton);

        HBox buttonContainer = new HBox(30);
        buttonContainer.setPrefSize(SCREEN_WIDTH, sceneContent.getPrefHeight() / 3);
        buttonContainer.setAlignment(Pos.CENTER);
        buttonContainer.getChildren().addAll(backToMenuButton);

        sceneContent.getChildren().addAll(titleContainer, selectionContainer,  buttonContainer);

        this.getChildren().addAll(sceneContent);
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
