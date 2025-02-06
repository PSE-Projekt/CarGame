package de.cargame.view;

import de.cargame.controller.api.GameInstanceAPI;
import de.cargame.controller.api.GameStateAPI;
import de.cargame.controller.api.PlayerAPI;
import de.cargame.controller.entity.GameState;
import de.cargame.view.game.GameScene;
import de.cargame.view.menu.MenuScene;
import de.cargame.view.scoreboard.ScoreBoardScene;
import de.cargame.view.selection.SelectionScene;
import javafx.application.Platform;
import javafx.stage.Stage;

import java.util.Map;

/**
 * Displays the Application stage. Initialized once by operations in the
 * Controller component and provides a 'render()' method for interacting with backend logic.
 */
public class ApplicationView {
    private final ApiHandler apiHandler;
    private final Map<GameState, CustomScene> sceneMap;
    private final Stage stage;
    private CustomScene currentScene;

    /**
     * Creates a new ApplicationView instance, which will create an ApiHandler and provide it
     * to the CustomScenes.
     */
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
        this.stage.setOnCloseRequest(event -> {
            Platform.exit(); // Ensure JavaFX exits completely
            System.exit(0);  // (Optional) Forcefully terminate the JVM
        });
        stage.setResizable(false);
        this.stage.setScene(sceneMap.get(GameState.MAIN_MENU));
        this.stage.show();
    }
    /**
     * Switches from the current Scene to the desired Scene.
     * @param newGameState the desired Scene
     */
    void switchScene(GameState newGameState) {
        currentScene = sceneMap.get(newGameState);

        if (currentScene == null) {
            throw new IllegalStateException(
                    "scene is not set for specified for current game mode: " + newGameState
            );
        }

        Platform.runLater(() -> {
            this.stage.setScene(currentScene);
            currentScene.setup();
        });
    }

    /**
     * renders the current scene.
     */
    public void renderGame() {
        GameState currentState = this.apiHandler.getGameStateApi().getGameState();

        // Handle each state explicitly
        if (currentState.equals(GameState.SCORE_BOARD)) {
            // Switch to the ScoreBoardScene
            // (GameScene) currentScene.renderGameOverAnimation(); //TODO
            this.switchScene(GameState.SCORE_BOARD);
        } else if (currentState.equals(GameState.IN_GAME)) {
            // Ensure the current scene is an instance of GameScene
            if (!(this.currentScene instanceof GameScene)) {
                throw new IllegalStateException("The current scene is not the game scene");
            }
            ((GameScene) currentScene).render();
        } else {
            // Invalid game state for rendering
            throw new IllegalStateException("Game state is not valid for rendering the game");
        }
    }
}