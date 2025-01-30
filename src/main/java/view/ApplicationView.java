package view;

import javafx.application.Application;
import javafx.stage.Stage;
import mockController.api.GameInstanceApi;
import mockController.api.GameStateApi;
import mockController.api.PlayerApi;
import mockController.entity.GameState;

import java.util.Map;

public class ApplicationView extends Application {
    private final ApiHandler apiHandler;
    private final Map<GameState, CustomScene> sceneMap;//TODO
    private CustomScene currentScene;
    private Stage stage;

    public ApplicationView(GameInstanceApi gameInstanceApi, GameStateApi gameStateApi, PlayerApi playerApi) {
        this.apiHandler = new ApiHandler(gameInstanceApi, gameStateApi, playerApi);
        try {
            stage = new Stage();
        } catch (Exception e) {
            e.printStackTrace();
        }

        //TODO: erzeuge zu jeder Scene eine Instanz
        //TODO: schreibe methode, die Szenenwechsel erlaubt (+refreshed?), womöglich applicationview als Param übergeben
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        this.stage = primaryStage;
        this.stage.setTitle("Car Game");
        this.stage.show();
    }

    public void render() {
        currentScene = sceneMap.get(apiHandler.getGameStateApi().getGameState());
        this.stage.setScene(currentScene);
        currentScene.render();
    }
}
