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

public class SelectionScene extends CustomScene {
    private final List<SelectionInstanceView> selectionInstanceViews;

    public SelectionScene(ApiHandler apiHandler) {
        super(apiHandler);
        this.configureRoot();
        this.selectionInstanceViews = new ArrayList<>();
    }

    private void configureSceneRoot() {
        VBox configurableRoot = ((VBox) this.getRoot());
        configurableRoot.getChildren().clear();

        configurableRoot.setMaxHeight(SCREEN_HEIGHT);
        configurableRoot.setMinHeight(SCREEN_HEIGHT);
        configurableRoot.setPrefHeight(SCREEN_WIDTH); //todo bug?????
        configurableRoot.setMaxWidth(SCREEN_WIDTH);
        configurableRoot.setMinWidth(SCREEN_WIDTH);
        configurableRoot.setPrefWidth(SCREEN_WIDTH);
        configurableRoot.setAlignment(Pos.CENTER);

        configurableRoot.getChildren().addAll(selectionInstanceViews);
    }

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
