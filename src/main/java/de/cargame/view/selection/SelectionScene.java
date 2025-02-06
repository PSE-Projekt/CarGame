package de.cargame.view.selection;

import de.cargame.controller.api.GameInstanceAPI;
import de.cargame.controller.api.PlayerAPI;
import de.cargame.controller.entity.GameMode;
import de.cargame.controller.entity.GameState;
import de.cargame.view.ApiHandler;
import de.cargame.view.CustomScene;
import javafx.geometry.Pos;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.List;

/**
 * This class provides a view over the two cars between the Players can choose, as well as the
 * buttons necessary to enter the other imminent scenes.
 */
public class SelectionScene extends CustomScene {
    private final List<SelectionInstanceView> selectionInstanceViews = new ArrayList<>();

    /**
     * Creates a new SelectionScene, which will be show the user his choices for the game.
     * @param apiHandler An instance of {@code ApiHandler} that provides functionality
     *                   for managing game state transitions as well as other key operations.
     */
    public SelectionScene(ApiHandler apiHandler) {
        super(apiHandler);
        this.configureRoot();
    }

    private void configureSceneRoot() {
        VBox configurableRoot = ((VBox) this.getRoot());
        configurableRoot.getChildren().clear();

        configurableRoot.setMaxHeight(SCREEN_HEIGHT);
        configurableRoot.setMinHeight(SCREEN_HEIGHT);
        configurableRoot.setPrefHeight(SCREEN_HEIGHT);
        configurableRoot.setMaxWidth(SCREEN_WIDTH);
        configurableRoot.setMinWidth(SCREEN_WIDTH);
        configurableRoot.setPrefWidth(SCREEN_WIDTH);
        configurableRoot.setAlignment(Pos.CENTER);

        configurableRoot.getChildren().addAll(selectionInstanceViews);
    }

    /**
     * checks whether every player made a decision, and switches to the GameScene if so
     */
    void proceedToGame() {

        PlayerAPI playerApi = apiHandler.getPlayerApi();
        GameInstanceAPI gameInstanceApi = apiHandler.getGameInstanceApi();

        for (SelectionInstanceView view : selectionInstanceViews) {
            if (!view.isReady()) {
                return;
            }
        }
        GameMode gameMode = apiHandler.getGameStateApi().getGameMode();
        String keyboardPlayerId = playerApi.getKeyboardPlayerId();
        String gamepadPlayerId = playerApi.getGamepadPlayerId();
        switch (gameMode){
            case SINGLEPLAYER:
                String playerOneId = apiHandler.getPlayerOneId();

                if(playerOneId.equals(keyboardPlayerId)){
                    playerApi.setPlaying(keyboardPlayerId, true);
                    playerApi.setPlaying(gamepadPlayerId, false);
                    gameInstanceApi.startGamePlayerKeyboard();

                } else if (playerOneId.equals(gamepadPlayerId)) {
                    playerApi.setPlaying(keyboardPlayerId, false);
                    playerApi.setPlaying(gamepadPlayerId, true);
                    gameInstanceApi.startGamePlayerGamePad();
                }
                break;

            case MULTIPLAYER:
                playerApi.setPlaying(gamepadPlayerId, true);
                playerApi.setPlaying(keyboardPlayerId, true);
                gameInstanceApi.startGamePlayerKeyboard();
                gameInstanceApi.startGamePlayerGamePad();
                break;

            case NOT_SET:
                throw new IllegalStateException("Game mode not set yet");

        }
        apiHandler.getGameStateApi().setGameState(GameState.IN_GAME);
        apiHandler.switchScene(GameState.IN_GAME);
    }

    @Override
    public void setup() {
        selectionInstanceViews.clear();
        GameMode currentGameMode = apiHandler.getGameStateApi().getGameMode();
        if (currentGameMode.equals(GameMode.MULTIPLAYER)) {
            this.selectionInstanceViews.add(new SelectionInstanceView(this, apiHandler, apiHandler.getPlayerApi().getKeyboardPlayerId()));
            this.selectionInstanceViews.add(new SelectionInstanceView(this, apiHandler, apiHandler.getPlayerApi().getGamepadPlayerId()));

        } else if (currentGameMode.equals(GameMode.SINGLEPLAYER)) {
            selectionInstanceViews.add(new SelectionInstanceView(this, apiHandler, this.apiHandler.getPlayerOneId()));
        }
        for (SelectionInstanceView selectionInstanceView : selectionInstanceViews) {
            selectionInstanceView.setup();
        }
        configureSceneRoot();
    }

}
