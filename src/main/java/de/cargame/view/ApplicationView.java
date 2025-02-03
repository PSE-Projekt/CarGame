package de.cargame.view;

import de.cargame.controller.api.GameInstanceAPI;
import de.cargame.controller.api.GameStateAPI;
import de.cargame.controller.api.PlayerAPI;
import de.cargame.controller.entity.GameState;
import de.cargame.view.game.GameScene;
import de.cargame.view.menu.MenuScene;
import de.cargame.view.scoreboard.ScoreBoardScene;
import de.cargame.view.selection.SelectionScene;
import javafx.stage.Stage;

import java.util.Map;

public class ApplicationView {
    private final ApiHandler apiHandler;
    private final Map<GameState, CustomScene> sceneMap;
    private CustomScene currentScene;
    private Stage stage;

    public ApplicationView(GameInstanceAPI gameInstanceApi, GameStateAPI gameStateApi, PlayerAPI playerApi, Stage stage) {
        this.stage = stage;
        this.apiHandler = new ApiHandler(gameInstanceApi, gameStateApi, playerApi, this);
        this.sceneMap = Map.of(
                GameState.MAIN_MENU, new MenuScene(this.apiHandler),
                GameState.CAR_SELECTION, new SelectionScene(apiHandler),
                GameState.IN_GAME, new GameScene(apiHandler),
                GameState.SCORE_BOARD, new ScoreBoardScene(apiHandler)
        );

        this.stage.setTitle("Car Game");
        stage.setResizable(false);
        this.stage.show();
    }

    void switchScene(GameState newGameState) {
        currentScene = sceneMap.get(newGameState);

        if (currentScene == null) {
            throw new IllegalStateException(
                    "scene is not set for specified for current game mode: " + newGameState
            );
        }

        this.stage.setScene(currentScene);
        currentScene.setup();
    }

    public void renderGame() {
        if (!this.apiHandler.getGameStateApi().getGameState().equals(GameState.IN_GAME)) {
            throw new IllegalStateException("Game state is not in game");
        } else if (!(this.currentScene instanceof GameScene)) {
            throw new IllegalStateException("the current scene is not the game scene");
        }

        ((GameScene) currentScene).render();
    }
}
