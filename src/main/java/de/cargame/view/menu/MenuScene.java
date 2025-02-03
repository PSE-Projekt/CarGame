package de.cargame.view.menu;

import de.cargame.config.GameConfig;
import de.cargame.view.ApiHandler;
import de.cargame.view.CustomScene;
import de.cargame.view.navigation.Direction;
import de.cargame.view.navigation.Navigator;
import de.cargame.view.navigation.Selectable;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class MenuScene extends CustomScene {

    private final Navigator assignedNavigator;
    public MenuScene(ApiHandler apiHandler){
        super(apiHandler);
        assignedNavigator = new MenuNavigator(apiHandler);
        prepareSceneContent();
    }

    private void prepareSceneContent() {
        Selectable dummy = this.assignedNavigator.getCurrentSelection();
        Selectable multiPlayerButton = new MultiPlayerButton();
        Selectable singlePlayerButton = new SinglePlayerButton();

        dummy.setNeighbour(Direction.LEFT, singlePlayerButton);
        dummy.setNeighbour(Direction.RIGHT, multiPlayerButton);

        singlePlayerButton.setNeighbour(Direction.RIGHT, multiPlayerButton);
        multiPlayerButton.setNeighbour(Direction.LEFT, singlePlayerButton);

        VBox root = (VBox) this.getRoot();

        HBox sceneContent = new HBox();

        sceneContent.setStyle("-fx-background-color: #131d34;");
        sceneContent.setPrefSize(GameConfig.SCREEN_WIDTH,GameConfig.SCREEN_HEIGHT/2);

        //TODO: css oder so damit das kürzer ist & 2 farben hat
        Text menuText = new Text("CarGame");
        menuText.setStyle("-fx-font-size: 30px; -fx-font-weight: bold; -fx-fill: #009783;");
        menuText.setFont(Font.loadFont(getClass().getResourceAsStream("/frontend/monomaniacOne.ttf"), 30));
        sceneContent.getChildren().addAll(singlePlayerButton,menuText, multiPlayerButton);

        root.getChildren().addAll(createBorder(),sceneContent,createBorder());
    }

    private Pane createBorder() {
        Pane border = new Pane();
        border.setStyle("-fx-background-color: black;");
        border.setPrefSize(GameConfig.SCREEN_WIDTH, GameConfig.SCREEN_HEIGHT / 4);
        return border;
    }

    @Override
    public void render() {
        //TODO
    }

    @Override
    public void setup() {
        assignedNavigator.reset();
        this.apiHandler.getInputReceiverGamePad().assignNavigator(this.assignedNavigator);
        this.apiHandler.getInputReceiverKeyboard().assignNavigator(this.assignedNavigator);
    }
}
