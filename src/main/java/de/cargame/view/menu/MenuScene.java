package de.cargame.view.menu;

import de.cargame.view.ApiHandler;
import de.cargame.view.CustomScene;
import de.cargame.view.navigation.Direction;
import de.cargame.view.navigation.Navigator;
import de.cargame.view.navigation.Selectable;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import static de.cargame.config.GameConfig.SCREEN_HEIGHT;
import static de.cargame.config.GameConfig.SCREEN_WIDTH;

public class MenuScene extends CustomScene {

    private final Navigator assignedNavigator;
    public MenuScene(ApiHandler apiHandler){
        super(apiHandler);
        assignedNavigator = new MenuNavigator(apiHandler);
        prepareSceneContent();
    }

    private void prepareSceneContent() {
        Selectable dummy = assignedNavigator.getCurrentSelectable();
        Selectable multiPlayerButton = new MultiPlayerButton();
        Selectable singlePlayerButton = new SinglePlayerButton();

        dummy.setNeighbour(Direction.LEFT, singlePlayerButton);
        dummy.setNeighbour(Direction.RIGHT, multiPlayerButton);

        singlePlayerButton.setNeighbour(Direction.RIGHT, multiPlayerButton);
        multiPlayerButton.setNeighbour(Direction.LEFT, singlePlayerButton);

        VBox root = (VBox) this.getRoot();

        HBox sceneContent = new HBox();

        sceneContent.setStyle("-fx-background-color: #131d34;");
        sceneContent.setPrefSize(SCREEN_WIDTH,SCREEN_HEIGHT/2);

        //TODO: css oder so damit das kürzer ist & 2 farben hat
        Text menuText = new Text("Welcome to CarGame Menu!");
        menuText.setStyle("-fx-font-size: 30px; -fx-font-weight: bold; -fx-fill: #009783;");
        menuText.setFont(Font.loadFont(getClass().getResourceAsStream("/frontend/monomaniacOne.ttf"), 30));
        sceneContent.getChildren().addAll(singlePlayerButton,menuText, multiPlayerButton);

        root.getChildren().addAll(createBorder(),sceneContent,createBorder());
    }

    private Pane createBorder() {
        Pane border = new Pane();
        border.setStyle("-fx-background-color: black;");
        border.setPrefSize(SCREEN_WIDTH, SCREEN_HEIGHT / 4);
        return border;
    }

    @Override
    public void render() {
        //TODO
    }
}
