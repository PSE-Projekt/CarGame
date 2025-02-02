package de.cargame.view;

import javafx.application.Application;
import javafx.stage.Stage;
import de.cargame.controller.entity.GameState;
import de.cargame.view.menu.MenuScene;

import java.util.HashMap;
import java.util.Map;

public class ApplicationView extends Application {
    private ApiHandler apiHandler;
    private final Map<GameState, CustomScene> sceneMap;
    private CustomScene currentScene;
    private Stage stage;

    public ApplicationView() {
        try {
            stage = new Stage();
        } catch (Exception e) {
            e.printStackTrace();
        }

        this.sceneMap = new HashMap<>();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        this.stage = primaryStage;
        this.stage.setTitle("Car Game");
        stage.setResizable(false);


        MenuScene menuScene = new MenuScene(apiHandler);
        this.stage.setScene(menuScene);

        this.stage.show();
    }

    public void render() {
        currentScene = sceneMap.get(apiHandler.getGameStateApi().getGameState());
        this.stage.setScene(currentScene);
        currentScene.render();
    }
}
