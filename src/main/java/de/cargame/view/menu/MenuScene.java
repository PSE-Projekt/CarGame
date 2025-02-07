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
import javafx.scene.text.Text;

/**
 * This scene is displayed upon launching the application and includes two buttons - one
 * for singleplayer mode and one for multiplayer mode. Both buttons will be redirecting the
 * player(s) to the according SelectionScene.
 */
public class MenuScene extends CustomScene {
    private final Navigator assignedNavigator;

    private final Selectable singlePlayerButton;
    private final Selectable multiPlayerButton;
    private final int SegmentCount = 3;

    /**
     * Creates a new MenuScene, which will be used at the start of the application.
     * @param apiHandler An instance of {@code ApiHandler} that provides functionality
     *                   for managing game state transitions as well as other key operations.
     */
    public MenuScene(ApiHandler apiHandler){
        super(apiHandler);
        assignedNavigator = new MenuNavigator(apiHandler);
        singlePlayerButton = new SinglePlayerButton();
        multiPlayerButton = new MultiPlayerButton();
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
        Text menuText = TextConfig.generateTitle("CarGame");
        menuText.setFill(ColorConfig.PRIMARY_MAIN);
        titleContainer.getChildren().add(menuText);
        titleContainer.setPrefSize(SCREEN_WIDTH, sceneContent.getPrefHeight() / SegmentCount);

        Pane spacer = new Pane();
        spacer.setPrefSize(SCREEN_WIDTH, sceneContent.getPrefHeight() / SegmentCount);

        HBox buttonContainer = new HBox(30);
        buttonContainer.setPrefSize(SCREEN_WIDTH, sceneContent.getPrefHeight() / SegmentCount);
        buttonContainer.setAlignment(Pos.CENTER);
        buttonContainer.getChildren().addAll(singlePlayerButton, multiPlayerButton);

        sceneContent.getChildren().addAll(titleContainer, spacer, buttonContainer);
        root.getChildren().addAll(sceneContent);
    }

    @Override
    public void setup() {
        assignedNavigator.reset();

        String gamePadPlayerId = apiHandler.getPlayerApi().getGamepadPlayerId();
        String keyboardPlayerId = apiHandler.getPlayerApi().getKeyboardPlayerId();

        this.apiHandler.getInputReceiverKeyboard().clear();
        this.apiHandler.getInputReceiverGamePad().clear();

        this.apiHandler.getInputReceiverKeyboard().assignNavigator(keyboardPlayerId, assignedNavigator);
        this.apiHandler.getInputReceiverGamePad().assignNavigator(gamePadPlayerId, assignedNavigator);
    }
}
