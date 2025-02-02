package de.cargame.view.selection;

import de.cargame.config.GameConfig;
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
        this.selectionInstanceViews = new ArrayList<>();
    }

    private void configureSceneRoot() {
        VBox configurableRoot = ((VBox) this.getRoot());

        configurableRoot.setMaxHeight(GameConfig.SCREEN_HEIGHT);
        configurableRoot.setMinHeight(GameConfig.SCREEN_HEIGHT);
        configurableRoot.setPrefHeight(GameConfig.SCREEN_WIDTH);
        configurableRoot.setMaxWidth(GameConfig.SCREEN_WIDTH);
        configurableRoot.setMinWidth(GameConfig.SCREEN_WIDTH);
        configurableRoot.setPrefWidth(GameConfig.SCREEN_WIDTH);
        configurableRoot.setAlignment(Pos.CENTER);

        configurableRoot.getChildren().addAll(selectionInstanceViews);
    }

    void proceedToGame(){
        for(SelectionInstanceView view : selectionInstanceViews){
            if(!view.isReady()){
                return;
            }
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
