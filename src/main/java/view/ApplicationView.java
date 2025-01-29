package view;

import javafx.application.Application;
import javafx.stage.Stage;
import mockController.api.GameInstanceApi;
import mockController.api.GameStateApi;
import mockController.api.PlayerApi;

import java.util.Map;

public class ApplicationView extends Application {
    private final ApiHandler apiHandler;
    private final Map<GameState, CustomScene> sceneMap;
    private CustomScene currentScene;
    private Stage stage;

    public ApplicationView(GameInstanceApi gameInstanceApi, GameStateApi gameStateApi, PlayerApi playerApi) {
        this.apiHandler = new ApiHandler(gameInstanceApi, gameStateApi, playerApi);
        try {
            stage = new Stage();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        this.stage = primaryStage;
        this.stage.setTitle("Car Game");
        this.stage.show();
    }

    public void render() {
        currentScene = sceneMap.get(apiHandler.getGameStateAPI().getCurrentGameState());
        this.stage.setScene(currentScene);
        currentScene.render();
    }
}
