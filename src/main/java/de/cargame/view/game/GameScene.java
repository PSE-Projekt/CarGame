package de.cargame.view.game;

import de.cargame.controller.api.PlayerAPI;
import de.cargame.controller.entity.GameMode;
import de.cargame.view.ApiHandler;
import de.cargame.view.CustomScene;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.List;

/**
 * A class displaying the core game by rendering its contents.
 */
public class GameScene extends CustomScene {
    private final List<GameInstanceView> gameInstanceViews = new ArrayList<>();
    /**
     * Creates a new GameScene, which will display the course of the game.
     * @param apiHandler An instance of {@code ApiHandler} that provides functionality
     *                   for managing game state transitions as well as other key operations.
     */
    public GameScene(ApiHandler apiHandler) {
        super(apiHandler);
        this.configureRoot();
    }

    /**
     * Renders the game course of each player currently playing.
     */
    public void render() {
        for (GameInstanceView gameInstanceView : gameInstanceViews) {
            gameInstanceView.render();
        }
    }

    @Override
    public void setup() throws IllegalStateException {
        VBox root = (VBox) this.getRoot();
        this.gameInstanceViews.clear();
        root.getChildren().clear();


        GameMode currentGameMode = this.apiHandler.getGameStateApi().getGameMode();
        PlayerAPI playerApi = this.apiHandler.getPlayerApi();


        if (currentGameMode.equals(GameMode.MULTIPLAYER)) {

            if (playerApi.getKeyboardPlayer() == null || playerApi.getGamepadPlayer() == null) {
                throw new IllegalStateException("One of the players does not exist");
            }

            this.gameInstanceViews.add(new GameInstanceView(apiHandler, playerApi.getKeyboardPlayerId()));
            this.gameInstanceViews.add(new GameInstanceView(apiHandler, playerApi.getGamepadPlayerId()));

        } else if (currentGameMode.equals(GameMode.SINGLEPLAYER)) {
            gameInstanceViews.add(new GameInstanceView(apiHandler, this.apiHandler.getPlayerOneId()));
        } else {
            throw new IllegalStateException("Game mode not specified yet");
        }

        this.configureRoot();

        root.getChildren().addAll(gameInstanceViews);

        this.apiHandler.getInputReceiverKeyboard().clear();
        this.apiHandler.getInputReceiverGamePad().clear();
    }
}
