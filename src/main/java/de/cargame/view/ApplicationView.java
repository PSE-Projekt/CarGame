package de.cargame.view;

import de.cargame.controller.api.GameInstanceApi;
import de.cargame.controller.api.GameStateApi;
import de.cargame.controller.api.PlayerApi;
import de.cargame.view.game.GameScene;
import de.cargame.view.scoreboard.ScoreBoardScene;
import de.cargame.view.selection.SelectionScene;
import javafx.application.Application;
import javafx.stage.Stage;
import de.cargame.controller.entity.GameState;
import de.cargame.view.menu.MenuScene;

import java.util.Map;

public class ApplicationView extends Application {
    private final ApiHandler apiHandler;
    private final Map<GameState, CustomScene> sceneMap;
    private CustomScene currentScene;
    private Stage stage;

    public ApplicationView(GameInstanceApi gameInstanceApi, GameStateApi gameStateApi, PlayerApi playerApi) {
        try {
            stage = new Stage();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }


        this.apiHandler = new ApiHandler(gameInstanceApi, gameStateApi, playerApi);

        this.sceneMap = Map.of(
                GameState.MAIN_MENU, new MenuScene(this.apiHandler),
                GameState.CAR_SELECTION, new SelectionScene(apiHandler),
                GameState.IN_GAME, new GameScene(apiHandler),
                GameState.SCORE_BOARD, new ScoreBoardScene(apiHandler)
        );
    }

    @Override
    public void start(Stage primaryStage) {
        this.stage = primaryStage;
        this.stage.setTitle("Car Game");
        stage.setResizable(false);
        this.stage.show();
    }

    public void render() {
        currentScene = sceneMap.get(apiHandler.getGameStateApi().getGameState());
        this.stage.setScene(currentScene);
        currentScene.setup();
        currentScene.render();
    }
}
