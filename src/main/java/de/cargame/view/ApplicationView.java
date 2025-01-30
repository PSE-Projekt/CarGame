package de.cargame.view;

import javafx.application.Application;
import javafx.stage.Stage;
import de.cargame.mockController.api.GameInstanceApi;
import de.cargame.mockController.api.GameStateApi;
import de.cargame.mockController.api.PlayerApi;
import de.cargame.mockController.entity.GameState;
import de.cargame.view.game.GameScene;
import de.cargame.view.menu.MenuScene;
import de.cargame.view.scoreboard.ScoreBoardScene;
import de.cargame.view.selection.SelectionScene;

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

        sceneMap = Map.of(
            GameState.MAIN_MENU, new MenuScene(apiHandler),
            GameState.CAR_SELECTION, new SelectionScene(apiHandler),
            GameState.IN_GAME, new GameScene(apiHandler),
            GameState.SCORE_BOARD, new ScoreBoardScene(apiHandler)
        );
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
