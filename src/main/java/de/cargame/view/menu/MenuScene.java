package de.cargame.view.menu;

import de.cargame.view.ApiHandler;
import de.cargame.view.CustomScene;
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

public class MenuScene extends CustomScene {
    private final Navigator assignedNavigator;
    private final Selectable singlePlayerButton;
    private final Selectable multiPlayerButton;
    private final int SegmentCount = 3;

    public MenuScene(ApiHandler apiHandler){
        super(apiHandler);
        assignedNavigator = new MenuNavigator(apiHandler);

        multiPlayerButton = new MultiPlayerButton();
        singlePlayerButton = new SinglePlayerButton();
        this.configureRoot();
        prepareSceneContent();
        setup();
    }

    private void prepareSceneContent() {
        singlePlayerButton.setNeighbour(Direction.RIGHT, multiPlayerButton);
        multiPlayerButton.setNeighbour(Direction.LEFT, singlePlayerButton);

        assignedNavigator.getInitialSelectable().setNeighbour(Direction.LEFT, singlePlayerButton);
        assignedNavigator.getInitialSelectable().setNeighbour(Direction.RIGHT, multiPlayerButton);

        VBox root = (VBox) this.getRoot();

        VBox sceneContent = new VBox();
        sceneContent.setStyle("-fx-background-color: #131d34;");
        sceneContent.setPrefSize(SCREEN_WIDTH, (double) SCREEN_HEIGHT /2);
        sceneContent.setAlignment(Pos.CENTER);

        StackPane titleContainer = new StackPane();
        Text menuText = TextConfig.makeH1("CarGame");
        menuText.setFill(ColorConfig.PRIMARY_MAIN);
        titleContainer.getChildren().add(menuText);
        titleContainer.setPrefSize(SCREEN_WIDTH, sceneContent.getPrefHeight() / SegmentCount);

        Pane spacer = new Pane();
        spacer.setPrefSize(SCREEN_WIDTH, sceneContent.getPrefHeight() / SegmentCount);

        HBox buttonContainer = new HBox(30);
        buttonContainer.setPrefSize(SCREEN_WIDTH, sceneContent.getPrefHeight() / SegmentCount);
        buttonContainer.setAlignment(Pos.CENTER);
        buttonContainer.getChildren().addAll(singlePlayerButton, multiPlayerButton);

        //TODO: css oder so damit das 2 farben hat

        sceneContent.getChildren().addAll(titleContainer, spacer, buttonContainer);
        root.getChildren().addAll(sceneContent);
    }

    @Override
    public void setup() {
        assignedNavigator.reset();
        this.apiHandler.getInputReceiverKeyboard().assignNavigator(this.assignedNavigator);
        this.apiHandler.getInputReceiverGamePad().assignNavigator(this.assignedNavigator);
    }
}
