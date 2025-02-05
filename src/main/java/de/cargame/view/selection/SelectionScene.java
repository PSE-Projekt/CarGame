package de.cargame.view.selection;

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
        for (SelectionInstanceView view : selectionInstanceViews) {
            if (!view.isReady()) {
                return;
            }
        }
        GameMode gameMode = apiHandler.getGameStateApi().getGameMode();
        switch (gameMode){
            case SINGLEPLAYER:
                apiHandler.getGameInstanceApi().startGamePlayerKeyboard(); //todo fix so right player game gets started
            break;
            case MULTIPLAYER:
                apiHandler.getGameInstanceApi().startGamePlayerKeyboard();
                apiHandler.getGameInstanceApi().startGamePlayerGamePad();
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
