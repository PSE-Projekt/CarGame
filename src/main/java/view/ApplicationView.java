package view;

import javafx.application.Application;
import javafx.stage.Stage;

import java.util.Map;

public class ApplicationView extends Application {
    private final APIHandler apiHandler;
    private final Map<GameState, CustomScene> sceneMap;
    private CustomScene currentScene;
    private Stage stage;

    public ApplicationView(GameInstanceAPI gameInstanceAPI, GameStateAPI gameStateAPI, PlayerAPI playerAPI) {
        this.apiHandler = new APIHandler(gameInstanceAPI, gameStateAPI, playerAPI);
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
