package de.cargame.view.selection;

import de.cargame.config.GameConfig;
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

    @Override
    public void setup() {
        for (SelectionInstanceView selectionInstanceView : selectionInstanceViews) {
            selectionInstanceView.setup();
        }
    }

}
