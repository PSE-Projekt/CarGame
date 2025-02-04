package de.cargame.view.menu;

import de.cargame.config.GameConfig;
import de.cargame.view.ApiHandler;
import de.cargame.view.CustomScene;
import de.cargame.view.navigation.Direction;
import de.cargame.view.navigation.Navigator;
import de.cargame.view.navigation.Selectable;
import javafx.geometry.Pos;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class MenuScene extends CustomScene {
    private final Navigator assignedNavigator;

    private final Selectable singlePlayerButton;
    private final Selectable multiPlayerButton;

    public MenuScene(ApiHandler apiHandler){
        super(apiHandler);
        assignedNavigator = new MenuNavigator(apiHandler);

        multiPlayerButton = new MultiPlayerButton();
        singlePlayerButton = new SinglePlayerButton();
        prepareSceneContent();
    }

    private void prepareSceneContent() {
        singlePlayerButton.setNeighbour(Direction.RIGHT, multiPlayerButton);
        multiPlayerButton.setNeighbour(Direction.LEFT, singlePlayerButton);

        assignedNavigator.getInitialSelectable().setNeighbour(Direction.LEFT, singlePlayerButton);
        assignedNavigator.getInitialSelectable().setNeighbour(Direction.RIGHT, multiPlayerButton);

        VBox root = (VBox) this.getRoot();

        this.configureRoot();

        HBox sceneContent = new HBox();

        sceneContent.setStyle("-fx-background-color: #131d34;");
        sceneContent.setPrefSize(GameConfig.SCREEN_WIDTH, (double) GameConfig.SCREEN_HEIGHT /2);

        //TODO: css oder so damit das kürzer ist & 2 farben hat
        Text menuText = new Text("CarGame");
        menuText.setStyle("-fx-font-size: 30px; -fx-font-weight: bold; -fx-fill: #009783;");
        menuText.setFont(Font.loadFont(getClass().getResourceAsStream("/frontend/monomaniacOne.ttf"), 30));
        sceneContent.getChildren().addAll(singlePlayerButton,menuText, multiPlayerButton);

        root.getChildren().addAll(sceneContent);
    }

    @Override
    public void setup() {
        assignedNavigator.reset();
        this.apiHandler.getInputReceiverGamePad().assignNavigator(this.assignedNavigator);
        this.apiHandler.getInputReceiverKeyboard().assignNavigator(this.assignedNavigator);
    }
}
